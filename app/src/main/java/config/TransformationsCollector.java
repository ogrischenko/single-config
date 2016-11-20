//package config;
//
//namespace RapidSoft.SingleConfig
//{
//    using System.Collections.Generic;
//    using System.Linq;
//
//    public class TransformationsCollector
//    {
//        private readonly SingleConfigLoader singleConfigLoader;
//
//        public TransformationsCollector(SingleConfigLoader singleConfigLoader)
//        {
//            this.singleConfigLoader = singleConfigLoader;
//        }
//
//        //public config.ConfigFileTransformation[] GetTransformations(config.SingleConfig singleConfig)
//        //{
//        //    OverrideConfig(includedSingleConfig, includedConfig.GlobalConfigFileTransformations, singleConfig.ExternalTransformationsFiles);
//
//        //    includedSingleConfig.ExternalTransformationsFiles = singleConfig.ExternalTransformationsFiles;
//
//        //}
//
//        private static void OverrideConfig(SingleConfig singleConfig, IEnumerable<ConfigFileTransformation> transformationOverrides, ExternalTransformationsFile[] externalTransformationsFiles)
//        {
//            // overrides transformations by global
//            if (singleConfig.GlobalConfigFileTransformations == null)
//            {
//                singleConfig.GlobalConfigFileTransformations = new ConfigFileTransformation[] { };
//            }
//
//            var globalConfigFileTransformations = new List<ConfigFileTransformation>(singleConfig.GlobalConfigFileTransformations);
//
//            OverrideTransformations(globalConfigFileTransformations, transformationOverrides);
//
//            //// add system transformations
//            //var systemTransformations = GetSystemTransformations();
//            //globalConfigFileTransformations.AddRange(systemTransformations);
//
//            singleConfig.GlobalConfigFileTransformations = globalConfigFileTransformations.ToArray();
//        }
//
//
//        private void AddExternalTransformations(SingleConfig singleConfig)
//        {
//            if (singleConfig.GlobalConfigFileTransformations == null)
//            {
//                singleConfig.GlobalConfigFileTransformations = new ConfigFileTransformation[] { };
//            }
//
//            var externalTransformationsFiles = singleConfig.ExternalTransformationsFiles;
//            var externalTransformations = new List<ConfigFileTransformation>();
//
//            // add external transformations
//            if (externalTransformationsFiles != null && externalTransformationsFiles.Length > 0)
//            {
//                foreach (var externalTransformationsFile in externalTransformationsFiles)
//                {
//                    var externalTransformationsConfig = singleConfigLoader.LoadSingleConfig(externalTransformationsFile.FilePath);
//
//                    if (externalTransformationsConfig.GlobalConfigFileTransformations != null)
//                    {
//                        foreach (var transformation in externalTransformationsConfig.GlobalConfigFileTransformations)
//                        {
//                            if (!singleConfig.GlobalConfigFileTransformations.Any(t => t.TransformationEqual(transformation)))
//                            {
//                                externalTransformations.Add(transformation);
//                            }
//                        }
//                    }
//                }
//            }
//
//            singleConfig.GlobalConfigFileTransformations =
//                singleConfig.GlobalConfigFileTransformations.Union(externalTransformations).ToArray();
//        }
//
//        private static void OverrideTransformations(List<ConfigFileTransformation> transformations, IEnumerable<ConfigFileTransformation> transformationOverrides)
//        {
//            if (transformationOverrides != null)
//            {
//                foreach (var transformationOverride in transformationOverrides)
//                {
//                    var transformation =
//                        transformations.Where(t => t.TransformationEqual(transformationOverride));
//
//                    foreach (var configFileTransformation in transformation)
//                    {
//                        var strategy = TransformationFactory.GetStrategy(configFileTransformation);
//                        strategy.OverrideTransformationParameters(transformationOverride);
//                    }
//                }
//            }
//        }
//    }
//}