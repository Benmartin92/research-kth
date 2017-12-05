import org.jgrapht.VertexFactory;

public class StringVertexFactory implements VertexFactory<String>{
    int index = 1;

    @Override
    public String createVertex(){
        return String.valueOf(index++);
    }
}
