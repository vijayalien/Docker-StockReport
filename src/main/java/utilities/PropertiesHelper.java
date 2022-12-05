package utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesHelper {

    static String env = getTestConfigs("Environment");

    public static String getTestConfigs(String propertyKey) {
        String propertyValue = "";
        try {
            String configFilePath = "src/main/resources/testConfig/config.properties";
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);
            propertyValue = prop.getProperty(propertyKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    public static String getValue(String application, String name) {
        String value = "";
        JSONObject jsonObject = null;
        JSONParser jsonParser = new JSONParser();
        try {
            jsonObject = jgetEnvJsonObject(getEnvJsonArray());
            value = ((JSONObject) jsonObject.get(application)).get(name).toString();
            return value;
        } catch (Exception e) {
            System.out.println("Failed to get the "+application+": "+name+ "value from the json file"+e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }

    }

    public static JSONObject jgetEnvJsonObject(JSONObject envJsonObject) {
        JSONObject obj = null;
        try {
            JSONArray envArray =(JSONArray)envJsonObject.get("Environment");
            for(int i=0;i<envArray.size();i++){
                obj=(JSONObject) envArray.get(i);
                if(obj.get("EnvironmentName").toString().equalsIgnoreCase(env)){
                    return obj;
                }
            }
        }catch(Exception var4){
            System.out.println("Failed to get the Environment Json object from the json file" +var4.getLocalizedMessage());
        }
        return  obj;
    }

    public static JSONObject getEnvJsonArray() {
        JSONObject envList = null;
        try {
        ClassLoader classLoader =  PropertiesHelper.class.getClassLoader();
        File file = new File(classLoader.getResource("dataStore").getFile());
        JSONParser jsonParser = new JSONParser();


            FileReader reader = new FileReader(file.getAbsolutePath()+File.separator+"data.json");
            Throwable var5 = null;
            try {
                Object obj = jsonParser.parse(reader);
                envList=(JSONObject)obj;
            } catch (Throwable var17) {
                var5 = var17;
                throw var17;
            } finally {
                if (reader != null) {
                    if (var5 != null) {
                        try {
                            reader.close();
                        } catch (Throwable var16) {
                            var5.addSuppressed(var16);
                        }
                    } else {
                        reader.close();
                    }
                }
            }
        } catch (FileNotFoundException var19) {
            System.out.println("Unable to find the json file");
            var19.printStackTrace();
        } catch (IOException var20) {
            System.out.println("Data.json file is corrupted");
        } catch (ParseException var21) {
            System.out.println("Failed to parse data.json file");
            var21.printStackTrace();
        }
        return envList;
    }
}
