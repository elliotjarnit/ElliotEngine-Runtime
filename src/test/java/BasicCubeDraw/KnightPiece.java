package BasicCubeDraw;

import dev.elliotjarnit.elliotengine.Exceptions.NotTriangleException;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Handlers.FileHandler;
import dev.elliotjarnit.elliotengine.Handlers.ObjHandler;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Objects.EObject;
import dev.elliotjarnit.elliotengine.Utils.Vector3;

import java.io.FileNotFoundException;

public class KnightPiece extends EObject {
    EColor EColor;

    public KnightPiece() {
        super();
        this.EColor = EColor.WHITE;
        this.loadFaces();
    }

    public KnightPiece(Vector3 origin) {
        super(origin);
        this.EColor = EColor.BLUE;
        this.loadFaces();
    }

    public void loadFaces() {
        try {
            String[] fileData = FileHandler.loadFile("src/test/java/BasicCubeDraw/xtree.obj");
            for (String line : fileData) {
                System.out.println(line);
            }
            EFace[] faces = ObjHandler.loadData(fileData);
            for (EFace face : faces) {
                face.setColor(this.EColor);
                System.out.println(face);
            }
            this.setFaces(faces);
        } catch (FileNotFoundException e) {
            System.out.println("File load error");
        } catch (NotTriangleException e) {
            System.out.println("Contains non triangle faces");
        }
    }

    @Override
    public void update() {

    }
}
