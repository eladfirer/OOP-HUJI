/**
 * PlayerFactory. this class is responsible for creating a renderer.
 */
public class RendererFactory {
    public RendererFactory(){}

    /**
     * creates a renderer.
     * @param type a string that represents the renderer type [console,void]
     * @return returns the renderer.
     */
    public static Renderer buildRenderer(String type, int size){
        type = type.toLowerCase();
        return switch (type){
            case "console" -> new ConsoleRenderer(size);
            case "void" -> new VoidRenderer();
            default -> null;
        };
    }
}
