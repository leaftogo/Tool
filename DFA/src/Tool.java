import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Tool {
    //将Object类对象转化成json字符串
    //请确保被转换对象所有的类（本身，和本身的各种值）必须为以下几种中的一个：
    //int String ArrayList HashMap Bean（Bean属性的类也必须包含在本行的五种情况之中）
    //非容器类的对象属性如果为空，将会在转化中被忽略
    public String toJSONString(Object object) {
        try{
            StringBuffer buffer = new StringBuffer();
            if(object instanceof ArrayList){
                buffer.append("[");
                ArrayList list = (ArrayList) object;
                for(int i=0;i<list.size();i++){
                    buffer.append(toJSONString(list.get(i)));
                    if(i != list.size()-1) buffer.append(",");
                }
                buffer.append("]");
            }else if(object instanceof HashSet){
                buffer.append("[");
                HashSet set = (HashSet) object;
                int i = 0;
                for(Object object1 : set){
                    buffer.append(toJSONString(object1));
                    if(i != set.size()-1) buffer.append(",");
                    i++;
                }
                buffer.append("]");
            }else if(object instanceof String){
                buffer.append("\"");
                buffer.append((String)object);
                buffer.append("\"");
            }else if(object instanceof Boolean){
                Boolean flag = (Boolean) object;
                if(flag.booleanValue()){
                    buffer.append("true");
                }else{
                    buffer.append("false");
                }
            }else if(object instanceof HashMap){
                HashMap map = (HashMap) object;
                Set set = map.keySet();
                buffer.append("{");
                int i=0;
                for(Object object1 : set){
                    i++;
                    buffer.append(toJSONString(object1));
                    buffer.append(":");
                    buffer.append(toJSONString(map.get(object1)));
                    if(i != set.size()) buffer.append(",");
                }
                buffer.append("}");
            }else if(object instanceof Integer){
                buffer.append("\"");
                buffer.append(String.valueOf((Integer)object));
                buffer.append("\"");
            }else{
                buffer.append("{");
                Field[] fields = object.getClass().getDeclaredFields();
                for(int i=0;i<fields.length;i++){
                    fields[i].setAccessible(true);
                    if(fields[i].get(object) == null) continue; //对象属性为空，忽略转化
                    buffer.append("\"");
                    buffer.append(fields[i].getName());
                    buffer.append("\"");
                    buffer.append(":");
                    fields[i].setAccessible(true);
                    buffer.append(toJSONString(fields[i].get(object)));
                    if(i != fields.length-1 && fields[i+1].get(object) != null){ //最后一个对象的值为null,不尾加","
                        buffer.append(",");
                    }
                }
                buffer.append("}");
            }
            return buffer.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "{\"error\":\"server method error\"}";
        }
    }
}
