import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {
    public String letter;
    public boolean ifend;
    public Set<Node> nextSet;

    public Node(String letter, boolean ifend, Set<Node> nextSet) {
        this.letter = letter;
        this.ifend = ifend;
        this.nextSet = nextSet;
    }

    public Node() {
        ifend = false;
    }

    //index所指向的String对应当前node的下一个node的letter
    public void add(String[] strArray,int index){
        int length = strArray.length;
        if(length == index){
            ifend = true;
            return;
        }
        String nextWord = strArray[index];
        if(nextSet == null){
            nextSet = new HashSet<>();
            Node node_next = new Node();
            node_next.letter = nextWord;
            nextSet.add(node_next);
            if(length>index+1){
                node_next.add(strArray,index+1);
                return;
            }
        }else{
            for(Node nextNode : nextSet){
                if(nextNode.letter.equals(nextWord)){
                    nextNode.add(strArray,index+1);
                    return;
                }
            }
            Node node_next = new Node();
            node_next.letter = nextWord;
            nextSet.add(node_next);
            if(length>index+1){
                node_next.add(strArray,index+1);
                return;
            }
        }
    }

    //index所指向的String对应当前node的下一个node的letter
    public boolean filte(String[] strArray,int index){
        int length = strArray.length;
        if(index == length){
            if(nextSet == null){
                return true;//目标字符串和一个存储值相同
            }else{
                return false;//目标字符串比存储值短
            }
        }
        String nextWord = strArray[index];
        if(nextSet == null){
            return true; //目标字符串比存储值长
        }else{
            for(Node nextNode : nextSet){
                if(nextNode.letter.equals(nextWord)){
                    return nextNode.filte(strArray,index+1);
                }
            }
            return false;//存储值中找不到和目标字符串equals的内容
        }
    }
}
