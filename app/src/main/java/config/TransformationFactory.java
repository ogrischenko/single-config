package config;

public class TransformationFactory
    {
        public static ITransformationStrategy GetStrategy(ConfigFileTransformation transformation)
        {
//            if (transformation instanceof RegExConfigFileTransformation)
//            {
//                return new RegExConfigFileTransformationStrategy((RegExConfigFileTransformation)transformation);
//            }
            if (transformation instanceof ConfigFileTransformation)
            {
                return new StringReplaceConfigFileTransformationStrategy((ConfigFileTransformation)transformation);
            }
            //if (transformation is GlobalConfigFileTransformation)
            //{
            //    var transform = (GlobalConfigFileTransformation)transformation;

            //    if (globalTransformations == null)
            //    {
            //        throw new InvalidOperationException("GlobalConfigFileTransformations section is missing");
            //    }

            //    var globalTransform = globalTransformations.FirstOrDefault(Predicate(transform));

            //    if (globalTransform == null)
            //    {
            //        throw new InvalidOperationException(NetString.Format("GlobalConfigFileTransformations with pattern {0} not found", transform.Pattern));
            //    }

            //    fileText = ExecuteTransformation(fileText, globalTransform, transform.Pattern, globalTransformations);
            //}
            throw new InvalidOperationException(String.format("TransformationStrategy %s not supported", transformation.getClass()));
        }
    }