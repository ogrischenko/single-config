package run;

import config.ConfigFileTransformation;
import netutil.NetString;

import java.util.ArrayList;
import java.util.Collections;

public class TransformationExtensions
    {
        public static Boolean TransformationEqual(ConfigFileTransformation t1, ConfigFileTransformation t2)
        {
            if (t1 == null && t2 == null)
            {
                return true;
            }

            if (t1 == null || t2 == null)
            {
                return false;
            }

            String[] t1Patterns = GetPatterns(t1);
            String[] t2Patterns = GetPatterns(t2);

            if (anySameKey(t1Patterns, t2Patterns)) {
                return true;
            }

            return false;
        }

        private static boolean anySameKey(String[] t1Patterns, String[] t2Patterns) {
            for (String str : t1Patterns) {
                for (String str2 : t2Patterns) {
                    if (str == str2) {
                        return true;
                    }
                }
            }
            return false;
        }

//        public static Boolean TransformationEqual(ConfigFileTransformation t1, String pattern)
//        {
//            if (t1 == null && pattern == null)
//            {
//                return true;
//            }
//
//            if (t1 == null || pattern == null)
//            {
//                return false;
//            }
//
//            String[] t1Patterns = GetPatterns(t1);
//            var t2Patterns = new[]
//            {
//                pattern
//            };
//
//            return t1Patterns.Any(p => t2Patterns.Any(p2 => p == p2));
//        }


        private static String[] GetPatterns(ConfigFileTransformation t1)
        {
            ArrayList<String> res = new ArrayList<String>();
            if (t1.Patterns != null)
            {
                Collections.addAll(res, t1.Patterns);
            }
            if (!NetString.IsNullOrEmpty(t1.Pattern))
            {
                res.add(t1.Pattern);
            }
            return res.toArray(new String[1]);
        }
    }