package run;

import config.*;
import netutil.NetFile;
import netutil.NetString;
import netutil.NetPath;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;

public class Configurator {
    private ArrayList<CmdArgument> arguments;
    private Logger log;
    private static String basePath;
    public static final String PatternSig = "@";

    private static ArrayList<String>ObsoleteTokens=new ArrayList<String>();
    private static ArrayList<String>UsedTokens=new ArrayList<String>();
    private SingleConfigLoader singleConfigLoader;
    private SingleConfig passedConfig;
    private ArrayList<ConfigFileTransformation> passedConfigTransforms;

    public Configurator(ArrayList<CmdArgument> arguments) {
        this.arguments = arguments;
        basePath = GetBasePath();
        log = new Logger(basePath);
        singleConfigLoader = new SingleConfigLoader(basePath);
    }

    public int Configure(String configFileName) {
        try {
            log.Write("Start");

            ConfigureInternal(configFileName);

            WriteObsolete();
            return 0;
        } catch (Exception e) {
            log.Error(e.toString());
            return 1;
        } finally {
            log.Write("End");
        }
    }

    private void ConfigureInternal(String configFileName) {
        ConfigureInternal(configFileName, null);
    }

    private void WriteObsolete() {
        String obsoleteTokensText = String.join(Environment.NewLine, ObsoleteTokens);

        if (!NetString.IsNullOrEmpty(obsoleteTokensText)) {
            log.Write("ObsoleteTokens");
            log.Write(obsoleteTokensText);
        }
    }

    private void ConfigureInternal(String configFileName, ConfigFileTransformation[] overrideTransforms) {
        log.Write(String.format("Base path:{0}", basePath));

        SingleConfig config = LoadSingleConfig(configFileName);

        if (passedConfig == null) {
            InitPassedFile(config);
        }

        if (config.IncludeSingleConfigs != null && config.IncludeSingleConfigs.length > 0) {
            for(IncludeSingleConfig include : config.IncludeSingleConfigs)
            {
                if (NetString.IsNullOrEmpty(include.FilePath)) {
                    continue;
                }

                ArrayList<ConfigFileTransformation> overrideTrans = MergeTransformations(
                        new ConfigFileTransformation[][]{

                                GetSystemTransformations(config.SrcRoot),
                                overrideTransforms,
                                include.GlobalConfigFileTransformations,
                                GetExternalTransformations(include.ExternalTransformationsFiles)
                        }
                );

                ReplaceTransform(overrideTrans);

                ConfigureInternal(include.FilePath, overrideTrans.toArray(new ConfigFileTransformation[0]));
            }
        }

        ArrayList<ConfigFileTransformation> transforms = MergeTransformations(
                new ConfigFileTransformation[][]{

                        GetSystemTransformations(config.SrcRoot),
                        overrideTransforms,
                        config.GlobalConfigFileTransformations,
                        GetExternalTransformations(config.ExternalTransformationsFiles)
                }
        );

        ReplaceTransform(transforms);

        if (config.ValidateRegExp != null) {
            TemplateValidateProcessor templateProcessor = new TemplateValidateProcessor(config.ValidateRegExp);
            ProcessTemplates(templateProcessor, config, transforms.toArray(new ConfigFileTransformation[0]));
        }

        ProcessTemplates(new TemplateConfigureProcessor(), config, transforms.toArray(new ConfigFileTransformation[0]));
    }

    private void ReplaceTransform(ArrayList<ConfigFileTransformation> overrideTrans) {
        for(ConfigFileTransformation transformation : overrideTrans)
        {
            if (!NetString.IsNullOrEmpty(transformation.ReplaceTo) && transformation.ReplaceTo.contains(PatternSig)) {
                transformation.ReplaceTo = ExecuteTransformations(transformation.ReplaceTo, overrideTrans);
            }
        }
    }

    private void InitPassedFile(SingleConfig config) {
        passedConfig = config;

        passedConfigTransforms = new ArrayList<ConfigFileTransformation>();

        passedConfigTransforms = MergeTransformations(
                new ConfigFileTransformation[][]{
                        passedConfigTransforms.toArray(new ConfigFileTransformation[0]),
                        passedConfig.GlobalConfigFileTransformations
                });

        if (config.IncludeSingleConfigs != null && config.IncludeSingleConfigs.length > 0) {
            for(IncludeSingleConfig include : config.IncludeSingleConfigs)
            {
                if (NetString.IsNullOrEmpty(include.FilePath)) {
                    continue;
                }

                passedConfigTransforms = MergeTransformations(
                        new ConfigFileTransformation[][]{
                        passedConfigTransforms.toArray(new ConfigFileTransformation[0]),
                        include.GlobalConfigFileTransformations});
            }
        }
    }

    private SingleConfig LoadSingleConfig(String configFileName) {
        SingleConfig singleConfig = singleConfigLoader.LoadSingleConfig(configFileName);
        log.Write(String.format("Config file path:%s", configFileName));
        return singleConfig;
    }

    private void ProcessTemplates(ITemplateProcessor templateProcessor, SingleConfig singleConfig, ConfigFileTransformation[] transformations) {
        ConfigureConfigFiles(templateProcessor, singleConfig, transformations);

        ConfigureDevConfigFiles(templateProcessor, singleConfig, transformations);
    }

    private void ConfigureConfigFiles(ITemplateProcessor templateProcessor, SingleConfig singleConfig, ConfigFileTransformation[] transformations) {
        if (singleConfig.ConfigFiles == null) {
            return;
        }

        for(ConfigFile configfile : singleConfig.ConfigFiles)
        {
            ProcessTemplate(templateProcessor, basePath, configfile, transformations, false);
        }
    }

    private void ConfigureDevConfigFiles(ITemplateProcessor templateProcessor, SingleConfig singleConfig, ConfigFileTransformation[] transformations) {
        if (singleConfig.DevConfigFiles == null) {
            return;
        }

        for(ConfigFile configfile : singleConfig.DevConfigFiles)
        {
            ProcessTemplate(templateProcessor, basePath, configfile, transformations, passedConfig.SkipDevConfigFilesCheck);
        }
    }

    private ConfigFileTransformation[] GetExternalTransformations(ExternalTransformationsFile[] externalTransformations) {
        ArrayList<ConfigFileTransformation> res = new ArrayList<ConfigFileTransformation>();

        if (externalTransformations == null) {
            return res.toArray(new ConfigFileTransformation[0]);
        }

        // add external transformations
        for(ExternalTransformationsFile externalTransformationsFile : externalTransformations)
        {
            SingleConfig externalTransformationsConfig = singleConfigLoader.LoadSingleConfig(externalTransformationsFile.FilePath);

            if (externalTransformationsConfig.GlobalConfigFileTransformations != null) {
                res = MergeTransformations(
                        new ConfigFileTransformation[][] {
                        res.toArray(new ConfigFileTransformation[0]), externalTransformationsConfig.GlobalConfigFileTransformations});
            }
        }

        return res.toArray(new ConfigFileTransformation[0]);
    }

    private ConfigFileTransformation[] GetSystemTransformations(String srcRoot) {
//        var res = arguments.Select(a = > new StringReplaceConfigFileTransformation
//        {
//            Pattern = a.Key,
//                    ReplaceTo = a.Value
//        }).ToList();
//
//        res.Add(
//                new StringReplaceConfigFileTransformation() {
//                    Pattern=PatternSig+SysTokenKeys.EnvMachineName+PatternSig,
//                    ReplaceTo=Environment.MachineName
//                });
//
//        String srcRootFullPath = basePath;
//
//        if (srcRoot != null) {
//            srcRootFullPath = NetPath.GetFullPath(NetPath.Combine(basePath, srcRoot));
//        }
//
//        res.Add(
//                new StringReplaceConfigFileTransformation() {
//                    Pattern=PatternSig+SysTokenKeys.SrcRoot+PatternSig,
//                    ReplaceTo=srcRootFullPath
//                });
//
//        res.Add(
//                new StringReplaceConfigFileTransformation() {
//                    Pattern=PatternSig+SysTokenKeys.SrcRootQuoted+PatternSig,
//                    ReplaceTo=srcRootFullPath.QuotStr()
//                });
//
//        return res.ToArray();
        return new ConfigFileTransformation[]{};

    }

    private void ProcessTemplate(ITemplateProcessor templateProcessor, String basePath, ConfigFile configfile, ConfigFileTransformation[] globalTransforms, Boolean skipNotExisted) {
        ArrayList<ConfigFileTransformation> configFileTransformations = MergeTransformations(
                new ConfigFileTransformation[][]{
                configfile.Transformations, globalTransforms});

        String templateFullFilePath = NetPath.Combine(basePath, configfile.FilePath);
        log.Write(String.format("File %s", templateFullFilePath));

        if (skipNotExisted && !NetFile.Exists(templateFullFilePath)) {
            log.Write(String.format("File %s not exist and skipNotExisted:%s. Skip.", templateFullFilePath, skipNotExisted));
            return;
        }

        String newFilePath = null;
        if (!NetString.IsNullOrEmpty(configfile.NewFilePath)) {
            newFilePath = NetPath.Combine(basePath, configfile.NewFilePath);
        }

        newFilePath = NetString.IsNullOrEmpty(newFilePath) ? templateFullFilePath : newFilePath;

//        try {
            if (configfile.SkipTransform) {
                templateProcessor.SkipTransform(templateFullFilePath, newFilePath);
            } else {
                String fileText = NetFile.ReadAllText(templateFullFilePath);

                fileText = ExecuteTransformations(fileText, configFileTransformations);
                templateProcessor.SaveTransformResult(templateFullFilePath, newFilePath, fileText, configfile.Encoding);
            }

//            if (configfile.WriteCopyTo != null) {
//                for(var copyFilePath in configfile.WriteCopyTo)
//                {
//                    var newCopyFilePath = GetFilePath(copyFilePath);
//                    templateProcessor.WriteCopyTo(newFilePath, newCopyFilePath);
//                }
//            }
//        } catch (Exception e) {
//            if (IsFileLocked(e)) {
//                log.Write(String.format("NetFile %s is locked. Skip.", newFilePath));
//            } else {
//                throw new RuntimeException(e);
//            }
//        }
    }

    private static ArrayList<ConfigFileTransformation> MergeTransformations(ConfigFileTransformation[][] transformationsArray) {
        ArrayList<ConfigFileTransformation> res = new ArrayList<ConfigFileTransformation>();

        for (int i = 0; i < transformationsArray.length; i++) {
            if (transformationsArray[i] == null) {
                continue;
            }

            for(ConfigFileTransformation transformation : transformationsArray[i])
            {
                if (!hasTransform(res, transformation)) {
                    res.add(transformation);
                }
            }
        }

        return res;
    }

    private static boolean hasTransform(ArrayList<ConfigFileTransformation> res, ConfigFileTransformation transformation) {
        for(ConfigFileTransformation item : res) {
            if (TransformationExtensions.TransformationEqual(item, transformation)) {
                return true;
            }
        }
        return false;
    }

//    private static Boolean IsFileLocked(Exception exception) {
//        int errorCode = Marshal.GetHRForException(exception) & ((1 << 16) - 1);
//        return errorCode == 32 || errorCode == 33;
//    }

    private String ExecuteTransformations(String text, ArrayList<ConfigFileTransformation> configFileTransformations) {
        for(ConfigFileTransformation transformation : configFileTransformations)
        {
            ArrayList<String > patterns = GetPatternsArray(transformation);
            for(String pattern : patterns)
            {
                if (NetString.IsNullOrEmpty(pattern)) {
                    continue;
                }

                text = ExecuteTransformation(text, transformation, pattern);
            }
        }

        text = ReplaceFullPath(text);

        return text;
    }

    private static String ReplaceFullPath(String fileText) {
        throw  new NotImplementedException();
//        var match = new Regex( @ "@@(FULLPATH:[^@]*)@@").Match(fileText);
//
//        while (match.Success) {
//            if (!String.IsNullOrEmpty(match.Groups[0].Value)) {
//                var matchText = match.Groups[0].Value;
//                var relativeFileName = matchText.Split(new[]{
//                    ":"
//                },StringSplitOptions.RemoveEmptyEntries)[1];
//                relativeFileName = relativeFileName.TrimEnd(new char[]
//                        {
//                                '@'
//                        });
//                fileText = fileText.Replace(matchText, NetPath.Combine(basePath, relativeFileName));
//            }
//            match = match.NextMatch();
//        }
//
//        return fileText;
    }

    private static ArrayList<String> GetPatternsArray(ConfigFileTransformation transformation) {
        ArrayList<String> res = new ArrayList<String>();
        if (transformation.Pattern != null) {
            res.add(transformation.Pattern);
        }
        if (transformation.Patterns != null) {
            Collections.addAll(res, transformation.Patterns);
        }
        return res;
    }

    private String ExecuteTransformation(String fileText, ConfigFileTransformation transformation, String selectedPattern) {
        ITransformationStrategy transformationStrategy = TransformationFactory.GetStrategy(transformation);

        CheckTransformForObsolete(fileText, selectedPattern);

        return transformationStrategy.Transform(fileText, selectedPattern);
    }

    private void CheckTransformForObsolete(String fileText, String selectedPattern) {
        if (fileText.indexOf(selectedPattern) != -1) {
            UsedTokens.add(selectedPattern);
            if (ObsoleteTokens.contains(selectedPattern)) {
                ObsoleteTokens.remove(selectedPattern);
            }
        }

        if (fileText.indexOf(selectedPattern) == -1 &&
                !IsSystemTransform(selectedPattern) &&
                !UsedTokens.contains(selectedPattern) &&
                PassedFileContainTransform(selectedPattern) &&
                !ObsoleteTokens.contains(selectedPattern)) {
            ObsoleteTokens.add(selectedPattern);
        }
    }

    private Boolean PassedFileContainTransform(String selectedPattern) {
        ConfigFileTransformation tran = new ConfigFileTransformation();
        tran.Patterns = new String[] { selectedPattern };

        return hasTransform(passedConfigTransforms, tran);
    }

    private Boolean IsSystemTransform(String selectedPattern) {
        return false;
//        return GetSystemTransformations().Any(t = > t.Pattern == selectedPattern);
    }

    public static String GetBasePath() {
        return Configurator.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

//    private String GetFilePath(String filePath) {
//        var logTxt = NetPath.Combine(basePath, filePath);
//        return logTxt;
//    }
}