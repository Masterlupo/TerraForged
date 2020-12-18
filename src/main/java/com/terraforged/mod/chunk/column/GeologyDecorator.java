/*
 * MIT License
 *
 * Copyright (c) 2020 TerraForged
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.terraforged.mod.chunk.column;

import com.terraforged.engine.world.geology.Stratum;
import com.terraforged.mod.api.biome.surface.ChunkSurfaceBuffer;
import com.terraforged.mod.api.biome.surface.SurfaceContext;
import com.terraforged.mod.api.chunk.column.ColumnDecorator;
import com.terraforged.mod.api.chunk.column.DecoratorContext;
import com.terraforged.mod.material.geology.GeoManager;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.IChunk;

public class GeologyDecorator implements ColumnDecorator, Stratum.Visitor<BlockState, SurfaceContext> {

    private final GeoManager geology;

    public GeologyDecorator(GeoManager geology) {
        this.geology = geology;
    }

    @Override
    public void decorate(IChunk chunk, DecoratorContext context, int x, int dy, int z) {

    }

    @Override
    public void decorate(ChunkSurfaceBuffer buffer, SurfaceContext context, int x, int y, int z) {
        int top = buffer.getSurfaceBottom();
        context.pos.setPos(x, y, z);
        geology.getGeology(context.biome).getStrata(x, z).downwards(x, top, z, context.depthBuffer.get(), context, this);
    }

    @Override
    public boolean visit(int y, BlockState state, SurfaceContext context) {
        context.pos.setY(y);
        ColumnDecorator.replaceSolid(context.buffer.getDelegate(), context.pos, state);
        return true;
    }
}
