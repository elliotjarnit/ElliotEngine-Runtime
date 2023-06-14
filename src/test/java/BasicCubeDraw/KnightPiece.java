package BasicCubeDraw;

import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

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
        this.EColor = EColor.WHITE;
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
        } catch (ObjHandler.NotTriangleException e) {
            System.out.println("Contains non triangle faces");
        }
    }

    @Override
    public void update() {

    }
}
