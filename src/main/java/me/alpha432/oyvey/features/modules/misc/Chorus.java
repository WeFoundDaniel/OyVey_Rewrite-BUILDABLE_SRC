package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.features.modules.*;
import net.minecraft.network.play.client.*;
import me.alpha432.oyvey.features.setting.*;
import java.util.*;
import me.alpha432.oyvey.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

public class Chorus extends Module
{
    Queue<CPacketPlayer> packets;
    Queue<CPacketConfirmTeleport> tpPackets;
    Setting<Boolean> cancel;
    
    public Chorus() {
        super("Chorus", "manipulates chorus", Category.PLAYER, true, false, false);
        this.packets = new LinkedList<CPacketPlayer>();
        this.tpPackets = new LinkedList<CPacketConfirmTeleport>();
        this.cancel = (Setting<Boolean>)this.register(new Setting("Cancel", (T)false));
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent event) {
        if (nullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook && this.cancel.getValue()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            this.packets.add(event.getPacket());
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketConfirmTeleport) {
            this.tpPackets.add(event.getPacket());
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        while (!this.packets.isEmpty()) {
            Chorus.mc.getConnection().sendPacket((Packet)this.packets.poll());
        }
        while (!this.tpPackets.isEmpty()) {
            Chorus.mc.getConnection().sendPacket((Packet)this.tpPackets.poll());
        }
    }
}
