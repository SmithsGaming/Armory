package com.Orion.Armory.API.Structures;

import com.Orion.Armory.Common.PathFinding.IPathComponent;
import com.Orion.Armory.Common.PathFinding.PathFinder;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Network.Messages.Structure.MessageOnCreateMasterEntity;
import com.Orion.Armory.Network.Messages.Structure.MessageOnCreateSlaveEntity;
import com.Orion.Armory.Network.Messages.Structure.MessageOnUpdateMasterData;
import com.Orion.Armory.Network.StructureNetworkManager;
import com.Orion.Armory.Util.Core.Coordinate;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 03.07.2015
 * 12:49
 * <p/>
 * Copyrighted according to Project specific license
 */
public final class StructureManager
{

    public static void joinSructure(IStructureComponent pStructureMember, IStructureComponent pNewComponent)
    {
        if (!(pStructureMember.isSlaved())) {
            pNewComponent.initiateAsSlaveEntity(pStructureMember);
            StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateSlaveEntity(pNewComponent, pStructureMember), new NetworkRegistry.TargetPoint(((TileEntity) pNewComponent).getWorldObj().provider.dimensionId,(double) pNewComponent.getLocation().getXComponent(),(double) pNewComponent.getLocation().getYComponent(),(double) pNewComponent.getLocation().getZComponent(), 128));
            try {
                (pStructureMember).registerNewSlave((TileEntity) pNewComponent);
                StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnUpdateMasterData(pStructureMember), new NetworkRegistry.TargetPoint(((TileEntity) pStructureMember).getWorldObj().provider.dimensionId, (double) pStructureMember.getLocation().getXComponent(), (double) pStructureMember.getLocation().getYComponent(), (double) pStructureMember.getLocation().getZComponent(), 128));
            } catch (Exception IAEx) {
                GeneralRegistry.iLogger.error("Failed to register a TE", IAEx);
            }

            return;
        }

        pNewComponent.initiateAsSlaveEntity(pStructureMember.getMasterEntity());
        StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateSlaveEntity(pNewComponent, pStructureMember.getMasterEntity()), new NetworkRegistry.TargetPoint(((TileEntity) pNewComponent).getWorldObj().provider.dimensionId, (double) pNewComponent.getLocation().getXComponent(), (double) pNewComponent.getLocation().getYComponent(), (double) pNewComponent.getLocation().getZComponent(), 128));
        try {
            pStructureMember.getMasterEntity().registerNewSlave((TileEntity) pNewComponent);
            StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnUpdateMasterData(pStructureMember.getMasterEntity()), new NetworkRegistry.TargetPoint(((TileEntity) pStructureMember.getMasterEntity()).getWorldObj().provider.dimensionId, (double) pStructureMember.getMasterEntity().getLocation().getXComponent(), (double) pStructureMember.getMasterEntity().getLocation().getYComponent(), (double) pStructureMember.getMasterEntity().getLocation().getZComponent(), 128));
        } catch (Exception IAEx) {
            GeneralRegistry.iLogger.error("Failed to register a TE on a remote master", IAEx);
        }

        return;
    }

    public static void mergeStructures(IStructureComponent pNewStructureMaster, IStructureComponent pComponentStructureMaster, IStructureComponent pCombiningStructureComponent)
    {
        //Add the individual slaves
        for (IStructureComponent tNewSlaveComponent : pComponentStructureMaster.getSlaveEntities().values())
        {
            joinSructure(pNewStructureMaster, tNewSlaveComponent);
        }

        //Add the structures Master, destroying the old Structure
        joinSructure(pNewStructureMaster, pComponentStructureMaster);
    }

    public static IStructureComponent splitStructure(IStructureComponent pOldMasterStructure, ArrayList<IStructureComponent> pSplittedComponents)
    {
        //Create the new Structures master Entity
        IStructureComponent tNewMasterComponent = pSplittedComponents.remove(0);
        tNewMasterComponent.initiateAsMasterEntity();
        StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateMasterEntity(tNewMasterComponent), new NetworkRegistry.TargetPoint(((TileEntity) tNewMasterComponent).getWorldObj().provider.dimensionId, (double) tNewMasterComponent.getLocation().getXComponent(), (double) tNewMasterComponent.getLocation().getYComponent(), (double) tNewMasterComponent.getLocation().getZComponent(), 128));

        //Let all the Slaves join the new Structure
        for(IStructureComponent tNewSlave : pSplittedComponents)
        {
            joinSructure(tNewMasterComponent, tNewSlave);

            pOldMasterStructure.removeSlave(tNewSlave.getLocation());
        }

        StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnUpdateMasterData(pOldMasterStructure), new NetworkRegistry.TargetPoint(((TileEntity) pOldMasterStructure).getWorldObj().provider.dimensionId, (double) pOldMasterStructure.getLocation().getXComponent(), (double) pOldMasterStructure.getLocation().getYComponent(), (double) pOldMasterStructure.getLocation().getZComponent(), 128));
        StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnUpdateMasterData(tNewMasterComponent), new NetworkRegistry.TargetPoint(((TileEntity) tNewMasterComponent).getWorldObj().provider.dimensionId, (double) tNewMasterComponent.getLocation().getXComponent(), (double) tNewMasterComponent.getLocation().getYComponent(), (double) tNewMasterComponent.getLocation().getZComponent(), 128));

        return tNewMasterComponent;
    }

    public static void createStructureComponent(IStructureComponent tNewComponent)
    {
        IStructureComponent tTargetStructure = null;

        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordiante(ForgeDirection.EAST, 1), tTargetStructure, tNewComponent);
        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordiante(ForgeDirection.WEST, 1), tTargetStructure, tNewComponent);
        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordiante(ForgeDirection.SOUTH, 1), tTargetStructure, tNewComponent);
        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordiante(ForgeDirection.NORTH, 1), tTargetStructure, tNewComponent);

        if (tTargetStructure == null)
        {
            tNewComponent.initiateAsMasterEntity();
            StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateMasterEntity(tNewComponent), new NetworkRegistry.TargetPoint(((TileEntity) tNewComponent).getWorldObj().provider.dimensionId, (double) tNewComponent.getLocation().getXComponent(), (double) tNewComponent.getLocation().getYComponent(), (double) tNewComponent.getLocation().getZComponent(), 128));
        }
    }

    private static IStructureComponent checkNewComponentSide(Coordinate pTargetCoordinate, IStructureComponent pTargetStructure, IStructureComponent pNewComponent)
    {
        TileEntity tEntity = getWorldObj(pNewComponent).getTileEntity(pTargetCoordinate.getXComponent(), pTargetCoordinate.getYComponent(), pTargetCoordinate.getZComponent());
        if (tEntity == null)
            return pTargetStructure;

        if (!(tEntity instanceof IStructureComponent))
            return pTargetStructure;

        if (!((IStructureComponent) tEntity).getStructureType().equals(pNewComponent.getStructureType()))
            return pTargetStructure;

        if (pTargetStructure == null)
        {
            joinSructure((IStructureComponent) tEntity, pNewComponent);
            return (IStructureComponent) tEntity;
        }

        if (((IStructureComponent) tEntity).getMasterEntity().getLocation().equals(pNewComponent.getMasterEntity().getLocation()))
            return pTargetStructure;

        if (((IStructureComponent) tEntity).isSlaved())
        {
            mergeStructures(pTargetStructure, ((IStructureComponent) tEntity).getMasterEntity(), pNewComponent);
        }
        else
        {
            mergeStructures(pTargetStructure, ((IStructureComponent) tEntity), pNewComponent);
        }

        return pTargetStructure;
    }

    public static void destroyStructureComponent(IStructureComponent tToBeDestroyedComponent)
    {
        IStructureComponent tMasterComponent = null;

        if (tToBeDestroyedComponent.isSlaved())
        {
            tToBeDestroyedComponent.getMasterEntity().removeSlave(tToBeDestroyedComponent.getLocation());
            StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnUpdateMasterData(tToBeDestroyedComponent.getMasterEntity()), new NetworkRegistry.TargetPoint(((TileEntity) tToBeDestroyedComponent.getMasterEntity()).getWorldObj().provider.dimensionId, (double) tToBeDestroyedComponent.getMasterEntity().getLocation().getXComponent(), (double) tToBeDestroyedComponent.getMasterEntity().getLocation().getYComponent(), (double) tToBeDestroyedComponent.getMasterEntity().getLocation().getZComponent(), 128));

            tMasterComponent = tToBeDestroyedComponent.getMasterEntity();
        }
        else
        {
            if(tToBeDestroyedComponent.getSlaveEntities().size() == 1)
            {
                tMasterComponent = (new ArrayList<IStructureComponent>(tToBeDestroyedComponent.getSlaveEntities().values())).get(0);
                tMasterComponent.initiateAsMasterEntity();
                StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateMasterEntity(tMasterComponent), new NetworkRegistry.TargetPoint(((TileEntity) tMasterComponent).getWorldObj().provider.dimensionId, (double) tMasterComponent.getLocation().getXComponent(), (double) tMasterComponent.getLocation().getYComponent(), (double) tMasterComponent.getLocation().getZComponent(), 128));
            }
            else if (tToBeDestroyedComponent.getSlaveEntities().size() > 1)
            {
                ArrayList<IStructureComponent> tComponentList = new ArrayList<IStructureComponent>(tToBeDestroyedComponent.getSlaveEntities().values());

                tMasterComponent = tComponentList.get(0);
                tMasterComponent.initiateAsMasterEntity();
                StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateMasterEntity(tMasterComponent), new NetworkRegistry.TargetPoint(((TileEntity) tMasterComponent).getWorldObj().provider.dimensionId, (double) tMasterComponent.getLocation().getXComponent(), (double) tMasterComponent.getLocation().getYComponent(), (double) tMasterComponent.getLocation().getZComponent(), 128));

                for (int tIndex = 1 ; tIndex < tComponentList.size(); tIndex++)
                {
                    joinSructure(tMasterComponent, tComponentList.get(tIndex));
                }
            }
        }

        if (tMasterComponent != null)
        {
            ArrayList<IStructureComponent> tNotConnectedComponents = validateStructureIntegrity(tMasterComponent, tToBeDestroyedComponent);
            while(!tNotConnectedComponents.isEmpty())
            {
                tNotConnectedComponents = validateStructureIntegrity(splitStructure(tMasterComponent, tNotConnectedComponents), tToBeDestroyedComponent);
            }
        }
    }

    public static ArrayList<IStructureComponent> validateStructureIntegrity(IStructureComponent pMasterComponent, IPathComponent pSeperatingComponent)
    {
        ArrayList<IStructureComponent> tNotConnectedComponents = new ArrayList<IStructureComponent>();

        for (IStructureComponent tSlave : pMasterComponent.getSlaveEntities().values())
        {
            if (!checkIfComponentStillConnected(pMasterComponent, tSlave, pSeperatingComponent))
                tNotConnectedComponents.add(tSlave);
        }

        for (IStructureComponent tSlave : tNotConnectedComponents)
        {
            GeneralRegistry.iLogger.info("Removing " + tSlave.getLocation().toString() + " from structure.");
            pMasterComponent.getSlaveEntities().remove(tSlave.getLocation());
        }

        return tNotConnectedComponents;
    }

    private static World getWorldObj(IStructureComponent pComponent)
    {
        return ((TileEntity) pComponent).getWorldObj();
    }

    private static boolean checkIfComponentStillConnected(IStructureComponent pMasterComponent, IStructureComponent pTargetComponent, IPathComponent pSplittingComponent)
    {
        GeneralRegistry.iLogger.info("Starting connection search between: " + pMasterComponent.getLocation().toString() + " to " + pTargetComponent.getLocation().toString());

        PathFinder  tConnectionChecker = new PathFinder(pMasterComponent, pTargetComponent, pSplittingComponent);
        return tConnectionChecker.isConnected();
    }


}
