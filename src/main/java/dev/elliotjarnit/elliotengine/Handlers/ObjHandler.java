package dev.elliotjarnit.elliotengine.Handlers;

import dev.elliotjarnit.elliotengine.Exceptions.NotTriangleException;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Utils.Vector3;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjHandler {
    public static EFace[] loadData(String[] data) throws NotTriangleException {
        ArrayList<EFace> faces = new ArrayList<>();
        ArrayList<Vector3> vertices = new ArrayList<>();
        // Load .obj data
        for (String line : data) {
            // Check if line is a vertex
            if (line.startsWith("v ")) {
                // Split line into components
                String[] components = line.split(" ");
                // Remove empty components
                components = Arrays.stream(components).filter(s -> !s.equals("")).toArray(String[]::new);
                // Create vertex
                Vector3 vertex = new Vector3(
                        Double.parseDouble(components[1]),
                        Double.parseDouble(components[2]),
                        Double.parseDouble(components[3])
                );

                // Add vertex to vertices
                vertices.add(vertex);
            }
            // Check if line is a face
            else if (line.startsWith("f ")) {
                // Split line into components
                String[] components = line.split(" ");
                // Remove empty components
                components = Arrays.stream(components).filter(s -> !s.equals("")).toArray(String[]::new);

                // Check if face is a triangle
                if (components.length != 4) {
                    throw new NotTriangleException("The .obj file contains a face that is not a triangle.");
                }

                // Create face
                EFace face = new EFace(
                        vertices.get(Integer.parseInt(components[1].split("/")[0]) - 1),
                        vertices.get(Integer.parseInt(components[2].split("/")[0]) - 1),
                        vertices.get(Integer.parseInt(components[3].split("/")[0]) - 1)
                );
                // Add face to faces
                faces.add(face);
            }
        }

        // Return faces
        return faces.toArray(new EFace[0]);
    }
}
