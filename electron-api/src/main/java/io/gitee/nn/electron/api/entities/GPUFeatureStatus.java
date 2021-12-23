package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPUFeatureStatus {
    /**
     * Canvas.
     */
    @JsonProperty("2d_canvas")
    private String canvas;
    /**
     * Flash.
     */
    @JsonProperty("flash_3d")
    private String flash3D;
    /**
     * Flash Stage3D.
     */
    @JsonProperty("flash_stage3d")
    private String flashStage3D;
    /**
     * Flash Stage3D Baseline profile.
     */
    @JsonProperty("flash_stage3d_baseline")
    private String flashStage3dBaseline;
    /**
     * Compositing.
     */
    @JsonProperty("gpu_compositing")
    private String gpuCompositing;
    /**
     * Multiple Raster Threads.
     */
    @JsonProperty("multiple_raster_threads")
    private String multipleRasterThreads;
    /**
     * Native GpuMemoryBuffers.
     */
    @JsonProperty("native_gpu_memory_buffers")
    private String nativeGpuMemoryBuffers;
    /**
     * Rasterization.
     */
    private String rasterization;
    /**
     * Video Decode.
     */
    @JsonProperty("video_decode")
    private String videoDecode;
    /**
     * Video Encode.
     */
    @JsonProperty("video_encode")
    private String videoEncode;
    /**
     * VPx Video Decode.
     */
    @JsonProperty("vpx_decode")
    private String vpxDecode;
    /**
     * WebGL.
     */
    private String webgl;
    /**
     * WebGL2.
     */
    private String webgl2;
}
