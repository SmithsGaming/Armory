package com.Orion.Armory.Util.Core;
/*
/  Rectangle
/  Created by : Orion
/  Created on : 27-4-2015
*/

public class Rectangle
{
    private Coordinate iTopLeftCoord;
    private Coordinate iLowerRightCoord;
    
    
    public int iWidth;
    public int iHeigth;

    public Rectangle() {
    }

    public Rectangle(int pTopLeftXCoord, int pTopLeftYCoord, int pWidth, int pHeigth) {
        iTopLeftCoord = new Coordinate(pTopLeftXCoord, pTopLeftYCoord);
        iLowerRightCoord = new Coordinate(pTopLeftXCoord + pWidth, pTopLeftYCoord + pHeigth);
        
        this.iWidth = pWidth;
        this.iHeigth = pHeigth;
    }

    public Coordinate getTopLeftCoord() {
        return this.iTopLeftCoord;
    }

    public Coordinate getLowerRightCoord() {
        return this.iLowerRightCoord;
    }

    public void set(int pTopLeftXCoord, int pTopLeftYCoord, int pWidth, int pHeigth) {
        iTopLeftCoord = new Coordinate(pTopLeftXCoord, pTopLeftYCoord);
        iLowerRightCoord = new Coordinate(pTopLeftXCoord + pWidth, pTopLeftYCoord + pHeigth);

        this.iWidth = pWidth;
        this.iHeigth = pHeigth;
    }

    public Rectangle offset(int pDeltaX, int pDeltaY) {
        this.iTopLeftCoord.iXCoord += pDeltaX;
        this.iTopLeftCoord.iYCoord += pDeltaY;

        this.iLowerRightCoord.iXCoord += pDeltaX;
        this.iLowerRightCoord.iYCoord += pDeltaY;

        return this;
    }

    public Rectangle include(Coordinate pCoord)
    {
        return this.include(pCoord.getXComponent(), pCoord.getYComponent());
    }

    public Rectangle include(int pXCoord, int pYCoord) {
        if(pXCoord < this.iTopLeftCoord.getXComponent()) {
            this.expand(pXCoord - this.iTopLeftCoord.getXComponent(), 0);
        }

        if(pXCoord >= this.iTopLeftCoord.getXComponent() + this.iWidth) {
            this.expand(pXCoord - this.iTopLeftCoord.getXComponent() - this.iWidth + 1, 0);
        }

        if(pYCoord < this.iTopLeftCoord.getYComponent()) {
            this.expand(0, pYCoord - this.iTopLeftCoord.getYComponent());
        }

        if(pYCoord >= this.iTopLeftCoord.getYComponent() + this.iHeigth) {
            this.expand(0, pYCoord - this.iTopLeftCoord.getYComponent() - this.iHeigth + 1);
        }

        return this;
    }

    public Rectangle include(Rectangle pRectangleToInclude) {
        this.include(pRectangleToInclude.getTopLeftCoord().getXComponent(), pRectangleToInclude.getTopLeftCoord().getYComponent());
        return this.include(pRectangleToInclude.getLowerRightCoord().getXComponent(), pRectangleToInclude.getLowerRightCoord().getYComponent());
    }

    public Rectangle expand(int px, int py) {
        if(px > 0) {
            this.iWidth += px;
        } else {
            this.getTopLeftCoord().iXCoord += px;
            this.iWidth -= px;
        }

        if(py > 0) {
            this.iHeigth += py;
        } else {
            this.getTopLeftCoord().iYCoord += py;
            this.iHeigth -= py;
        }

        return this;
    }

    public boolean contains(Coordinate pCoord)
    {
        return this.contains(pCoord.getXComponent(), pCoord.getYComponent());
    }

    public boolean contains(int pXCoord, int pYCoord) {
        return this.getTopLeftCoord().getXComponent() <= pXCoord && pXCoord < this.getTopLeftCoord().getXComponent() + this.iWidth && this.getTopLeftCoord().getYComponent() <= pYCoord && pYCoord < this.getTopLeftCoord().getYComponent() + this.iHeigth;
    }

    public boolean intersects(Rectangle pRectangleToCheck) {
        return pRectangleToCheck.getTopLeftCoord().getXComponent() + pRectangleToCheck.iWidth > this.getTopLeftCoord().getXComponent() && pRectangleToCheck.getTopLeftCoord().getXComponent() < this.getTopLeftCoord().getXComponent() + this.iWidth && pRectangleToCheck.getTopLeftCoord().getYComponent() + pRectangleToCheck.iHeigth > this.getTopLeftCoord().getYComponent() && pRectangleToCheck.getTopLeftCoord().getYComponent() < this.getTopLeftCoord().getYComponent() + this.iHeigth;
    }

    public int area() {
        return this.iWidth * this.iHeigth;
    }
}
