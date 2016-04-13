package cn.accessbright.blade.captcha;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/4/13.
 */
@Component("cacheCaptchaHolder")
public class CacheCaptchaHolder implements CaptchaHolder {

    private CacheManager cacheManager;

    private Cache cache;

    public CacheCaptchaHolder(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        cache = cacheManager.getCache(CAPTCHA_CACHE_KEY);
    }

    @Override
    public String put(String captchaKey, String captchaValue) {
        String oldValue = cache.get(captchaKey, String.class);
        cache.put(captchaKey, captchaValue);
        return oldValue;
    }

    @Override
    public String get(String captchaKey) {
        return cache.get(captchaKey, String.class);
    }

    @Override
    public String remove(String captchaKey) {
        String oldValue = cache.get(captchaKey, String.class);
        cache.evict(captchaKey);
        return oldValue;
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
