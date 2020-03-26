package cn.xjh3.Net.WebServer02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Webcontext {
    private List<Entity> E=null;
    private   List<Mapping> M=null;
    private Map<String,String> EntityMap=new HashMap<>();//KEY->Entity里的name，VALUE-》Entity里的class
    private Map<String,String> MappingMap=new HashMap<>();//KEY->Mapping里的pattern，VALUE-》Mapping里的name
    public Webcontext(List<Entity> E, List<Mapping> M) {
        this.E = E;
        this.M = M;

        for(Entity e:E){
            EntityMap.put(e.getName(),e.getClz());//将Handler得到的entity list转换为map
        }

        for(Mapping m:M){
            for(String pattern:m.getPatterns()){
                MappingMap.put(pattern,m.getName());//将得到的mapping list转换为map，因为mapping内还有一个set容器，双重循环
            }
        }
    }

    //通过URL路径找到class：由url-pattern找到servlet-name再找到servlet-class
    public String getclz(String pattern){//得到类名
        String name=MappingMap.get(pattern);
        return EntityMap.get(name);
    }
}
