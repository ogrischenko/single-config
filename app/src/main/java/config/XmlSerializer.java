//package config;
//
//using System.IO;
//using System.Xml;
//using System.Xml.Linq;
//
//namespace RapidSoft.SingleConfig
//{
//    public static class XmlSerializer
//    {
//        public static XElement SerializeToElement<T>(T obj)
//        {
//            if (obj == null)
//            {
//                return null;
//            }
//            var serialize = Serialize(obj);
//            if (String.IsNullOrEmpty(serialize))
//            {
//                return null;
//            }
//            return XElement.Parse(serialize);
//        }
//
//        public static XmlDocument SerializeToXmlDocument<T>(T obj)
//        {
//            if (obj == null)
//            {
//                return null;
//            }
//            var serialize = Serialize(obj);
//            if (String.IsNullOrEmpty(serialize))
//            {
//                return null;
//            }
//            var doc = new XmlDocument();
//            doc.LoadXml(serialize);
//            return doc;
//        }
//
//        public static String Serialize<T>(T obj)
//        {
//            if (obj == null)
//            {
//                return null;
//            }
//            if (obj.Equals(default(T)))
//            {
//                return null;
//            }
//            using (var sw = new StringWriter())
//            {
//                var ser = new System.Xml.Serialization.XmlSerializer(obj.GetType());
//                ser.Serialize(sw, obj);
//                return sw.ToString();
//            }
//        }
//
//        public static T Deserialize<T>(XElement element) where T : class
//        {
//            if (element == null)
//            {
//                return null;
//            }
//
//            using (var sr = element.CreateReader())
//            {
//                var ser = new System.Xml.Serialization.XmlSerializer(typeof(T));
//                return (T)ser.Deserialize(sr);
//            }
//        }
//
//        public static T Deserialize<T>(String xmlString) where T : class
//        {
//            if (String.IsNullOrEmpty(xmlString))
//            {
//                return null;
//            }
//            if (String.IsNullOrEmpty(xmlString))
//            {
//                return default(T);
//            }
//
//            using (var sr = new StringReader(xmlString))
//            {
//                var ser = new System.Xml.Serialization.XmlSerializer(typeof(T));
//                return (T)ser.Deserialize(sr);
//            }
//
//        }
//    }
//}