package config;

import netutil.NetString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateValidateProcessor implements ITemplateProcessor
    {
        private String validateRegExp;

        public TemplateValidateProcessor(String validateRegExp)
        {
            this.validateRegExp = validateRegExp;
        }

        public void SkipTransform(String fullFilePath, String newFilePath)
        {
            
        }

        public void SaveTransformResult(String templateFullFilePath, String newFilePath, String fileText, String encoding)
        {
            if (NetString.IsNullOrEmpty(validateRegExp))
            {
                return;
            }

            Pattern pattern = Pattern.compile(validateRegExp);

            Matcher m = pattern.matcher(fileText);

            StringBuilder sb = new StringBuilder();

            while (m.find())
            {
                sb.append(String.format("%s%s", m.group(1), Environment.NewLine));
            }

            if (sb.length() > 0)
            {
                throw new RuntimeException(String.format("Validate template error File %s Regexp:%s Found:%s", templateFullFilePath, validateRegExp, sb));
            }
        }

        public void WriteCopyTo(String newFilePath, String newCopyFilePath)
        {
            
        }
    }