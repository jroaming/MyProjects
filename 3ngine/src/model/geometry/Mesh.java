package model.geometry;

import controller.EngineController;
import java.util.ArrayList;
import java.util.Iterator;
import static java.util.Objects.isNull;
import utils.UtilsMath;

/**
 *
 * @author Joel
 */
public class Mesh {
    private String name;
    private Vertex rot; // rotation in radians
    private Vertex addToRot;
    private Vertex pos; // origin vertex position
    private Vertex addToPos;
    private ArrayList<Triangle> tris;  // "polygons"
    
    public Mesh(String name, Vertex initPos) {
        this.tris = new ArrayList<Triangle>();
        this.name = name;
        
        this.pos = (isNull(initPos))? new Vertex(0.0f, 0.0f, 0.0f) : new Vertex(initPos);
        this.addToPos = new Vertex(0.0f, 0.0f, 0.0f);
        
        this.rot = new Vertex(0.0f, 0.0f, 0.0f);
        this.addToRot = new Vertex(0.0f, 0.0f, 0.0f);
        //this.addToRot = new Vertex(UtilsMath.DegToRads(45), UtilsMath.DegToRads(5), -UtilsMath.DegToRads(15));
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Triangle> getTris() {
        return tris;
    }
    public void setTris(ArrayList<Triangle> tris) {
        this.tris = tris;
    }
    public Vertex getRot() {
        return rot;
    }
    public void setRot(Vertex rot) {
        this.rot = rot;
    }
    public Vertex getAddToRot() {
        return addToRot;
    }
    public void setAddToRot(Vertex addToRot) {
        this.addToRot = addToRot;
    }
    public Vertex getPos() {
        return pos;
    }
    public void setPos(Vertex pos) {
        this.pos = pos;
    }
    public Vertex getAddToPos() {
        return addToPos;
    }
    public void setAddToPos(Vertex addToPos) {
        this.addToPos = addToPos;
    }
    
    
    
    
    public void addTriangle(Triangle t) {
        this.tris.add(t);
    }
    public void addTriangle(Vertex v1, Vertex v2, Vertex v3) {
        this.tris.add(new Triangle(v1,v2,v3));
    }
    public void addTriangle(int id, Vertex v1, Vertex v2, Vertex v3) {
        this.tris.add(new Triangle(id,v1,v2,v3));
    }
    public Triangle getTriangle(int i) {
        return this.tris.get(i);
    }
    public void removeTriangle(int i) {
        this.tris.remove(i);
    }

    public void loadDepthValues() {
        for (Triangle t:this.tris)
            t.calculateDepthValue();
    }
    
    
    public void delete() {
        for (Triangle t:this.tris) {
            t.delete();
        }
        this.tris = null;
    }
}
