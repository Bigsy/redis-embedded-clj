# redis-embedded-clj

Embedded redis for clojure - based on https://github.com/signalapp/embedded-redis

## Usage

### Development:

```clojure
(require 'redis-embedded-clj.core)

;; Start an embedded redis with default port:
(init-rd)

;; another call will halt the previous system:
(init-rd)

;; When you're done:
(halt-rd!)
```

### Testing:

**NOTE**: these will halt running rd-embedded instances

```clojure
(require 'clojure.test)

(use-fixtures :once with-rd-fn)

(defn around-all
  [f]
  (with-rd-fn (merge default-config
                           {:port 6379})
                    f))

(use-fixtures :once around-all)

;;; You can also wrap ad-hoc code in init/halt:
(with-rd default-config
	,,, :do-something ,,,)
```

