/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.PathFinding;

import java.util.ArrayList;

public class Path {

    ArrayList<IPathComponent> iComponents;
    IPathComponent iStartNode = null;
    IPathComponent iEndNode = null;

    public Path(IPathComponent pStartNode, IPathComponent pEndNode) {
        this(pStartNode, pEndNode, new ArrayList<IPathComponent>());
    }

    public Path(IPathComponent pStartNode, IPathComponent pEndNode, ArrayList<IPathComponent> pComponents) {
        iComponents = pComponents;
        iStartNode = pStartNode;
        iEndNode = pEndNode;
    }

    public void startConstructingReversePath() {
        iComponents.add(iEndNode);
    }

    public void endConstructingReversePath() {
        if (!iComponents.contains(iStartNode))
            iComponents.add(iStartNode);
    }

    public ArrayList<IPathComponent> getComponents() {
        return iComponents;
    }

    public IPathComponent getStartNode() {
        return iStartNode;
    }

    public IPathComponent getEndNode() {
        return iEndNode;
    }
}
