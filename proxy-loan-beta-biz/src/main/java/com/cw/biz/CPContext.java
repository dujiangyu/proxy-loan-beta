
package com.cw.biz;

import java.io.Serializable;

/**
 *
 */
public class CPContext {
    private static final ThreadLocal<CPContext> LOCAL = new ThreadLocal<CPContext>() {
        protected CPContext initialValue() {
            return new CPContext();
        }
    };

    private String gid;

    private Long merchantId;

    private SeUserInfo seUserInfo;

    private CPContext() {

    }

    public static CPContext getContext() {
        return LOCAL.get();
    }

    public static void putContext(CPContext cpContext) {
        LOCAL.set(cpContext);
    }

    public static void removeContext() {
        LOCAL.remove();
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public SeUserInfo getSeUserInfo() {
        return seUserInfo;
    }

    public void setSeUserInfo(SeUserInfo seUserInfo) {
        this.seUserInfo = seUserInfo;
    }

    /**
     * 用户信息
     */
    public static class SeUserInfo implements Serializable {
        private Long id;
        private String username;
        private String displayName;
        private Long merchantId;
        private Long rId;//关联id
        private String type;
        private String wechatId;
        private String phone;
        private String channel;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Long merchantId) {
            this.merchantId = merchantId;
        }

        public Long getrId() {
            return rId;
        }

        public void setrId(Long rId) {
            this.rId = rId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWechatId() {
            return wechatId;
        }

        public void setWechatId(String wechatId) {
            this.wechatId = wechatId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }
    }
}
