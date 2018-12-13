import java.util.HashMap;
import java.util.List;

public class NodePool {
    public HashMap<String,Node> saveMap;

    public NodePool() {
        saveMap = new HashMap<>();
    }

    public boolean add(String str){
        if(str.length() == 0) return false;
        String[] array = str.split("");
        Node node = new Node();
        node.letter = array[0];
        node.add(array,1);
        saveMap.put(array[0],node);
        return true;
    }

    public boolean filte(String str){
        if(str.length() == 0) return false;
        String[] array = str.split("");
        int index = 0;
        int length = array.length;
        boolean flag = false;
        while(index<length && !flag){
            String firstWord = array[index];
            if(!saveMap.containsKey(firstWord)){

            }else{
                flag = saveMap.get(firstWord).filte(array,index);
            }
            index++;
        }
        return flag;
    }

    public static void main(String[] args) {
        NodePool nodePool = new NodePool();
        Tool tool = new Tool();
        nodePool.add("测试");
        System.out.println(tool.toJSONString(nodePool));
        nodePool.add("s");
        System.out.println(tool.toJSONString(nodePool));
        if( nodePool.filte("测试")){
            System.out.println("不合法");
        }
    }
}
