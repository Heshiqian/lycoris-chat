package cn.heshiqian.lycoris.core.storeage;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Heshiqian
 * @version 1.0.0
 * @since 2023/7/26
 */
@Data
public class LycorisFile {

    private String name;

    private String hash;

    private String suffix;

    private long size;

    private String location;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

}
