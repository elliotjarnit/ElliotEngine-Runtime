package dev.elliotjarnit.ElliotEngine.Handlers;

import dev.elliotjarnit.ElliotEngine.Exceptions.NotTriangleException;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

import java.util.ArrayList;

public class BlendHandler {
    public static EFace[] loadData(String[] data) throws NotTriangleException {
        ArrayList<EFace> faces = new ArrayList<>();
        Vector3[] verticesTemp = new Vector3[3];
        // Load .blend data
        for (String line : data) {
            // Check if line is a vertex
            if (line.startsWith("v ")) {
                // Split line into components
                String[] components = line.split(" ");
                // Create vertex
                Vector3 vertex = new Vector3(
                        Double.parseDouble(components[1]),
                        Double.parseDouble(components[2]),
                        Double.parseDouble(components[3])
                );

                // Check if verticesTemp is full
                if (verticesTemp[2] != null) {
                    throw new NotTriangleException("The .blend file contains a face that is not a triangle.");
                }

                // Add vertex to verticesTemp
                for (int i = 0; i < verticesTemp.length; i++) {
                    if (verticesTemp[i] == null) {
                        verticesTemp[i] = vertex;
                        break;
                    }
                }
            }
            // Check if line is a face
            else if (line.startsWith("f ")) {
                // Split line into components
                String[] components = line.split(" ");
                // Create face
                EFace face = new EFace(
                        verticesTemp[Integer.parseInt(components[1].split("/")[0]) - 1],
                        verticesTemp[Integer.parseInt(components[2].split("/")[0]) - 1],
                        verticesTemp[Integer.parseInt(components[3].split("/")[0]) - 1]
                );
                // Add face to faces
                faces.add(face);
            }
        }
        // Return faces
        return faces.toArray(new EFace[0]);
    }
}
