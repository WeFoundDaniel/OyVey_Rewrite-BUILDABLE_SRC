package me.alpha432.oyvey.util;

import net.minecraftforge.fml.common.asm.transformers.*;
import java.io.*;

public final class Transformer extends AccessTransformer
{
    public Transformer() throws IOException {
        super("oyvey_at.cfg");
    }
}
