package com.Orion.Armory.Common.PathFinding;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 03.07.2015
 * 14:42
 * <p/>
 * Copyrighted according to Project specific license
 */
public class Path {

    ArrayList<IPathComponent> iComponents;
    IPathComponent iStartNode = null;
    IPathComponent iEndNode = null;

    public Path(IPathComponent pStartNode, IPathComponent pEndNode)
    {
        this(pStartNode, pEndNode, new ArrayList<IPathComponent>());
    }

    public Path(IPathComponent pStartNode, IPathComponent pEndNode, ArrayList<IPathComponent> pComponents)
    {
        iComponents = pComponents;
        iStartNode = pStartNode;
        iEndNode = pEndNode;
    }

    public void startConstructingReversePath()
    {
        iComponents.add(iEndNode);
    }

    public void endConstructingReversePath()
    {
        if (!iComponents.contains(iStartNode))
            iComponents.add(iStartNode);
    }

    public ArrayList<IPathComponent> getComponents()
    {
        return iComponents;
    }

    public IPathComponent getStartNode()
    {
        return iStartNode;
    }

    public IPathComponent getEndNode()
    {
        return iEndNode;
    }
}
