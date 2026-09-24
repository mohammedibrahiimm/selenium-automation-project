package drivers;

import Utils.LogUtils;

public enum Browser {
    CHROME {
        @Override
        public AbstractDriver getDriverFactory() {
            LogUtils.info("Chrome Factory is called");
            return new ChromeFactory();
        }
    },
    EDGE {
        @Override
        public AbstractDriver getDriverFactory()
        {
            LogUtils.info("Edge Factory is called");
            return new EdgeFactory();
        }
    },
    FIREFOX {
        @Override
        public AbstractDriver getDriverFactory() {
            LogUtils.info("Firefox Factory is called");
            return new FirefoxFactory();
        }
    },
    SAFARI{
        @Override
        public AbstractDriver getDriverFactory() {
            LogUtils.info("Safari Factory is called");
            return new SafariFactory();
        }
    };

    public abstract AbstractDriver getDriverFactory();
}
