(ns leiningen.kotlin
  (:require [leiningen.core.eval :refer [eval-in-project]]
            [leiningen.core.project :refer [merge-profiles]]))

(defn kotlin
  "I don't do a lot."
  [project & args]
  (let [ksrc (:kotlin-source-path project)
        target (or (:compile-path project) "target")
        version (or (:kotlin-compiler-version project) "1.0.1-2")
        p (merge-profiles project [{:dependencies [['org.jetbrains.kotlin/kotlin-compiler version]]}])
        p (dissoc p :prep-tasks)]
    (when ksrc
      (eval-in-project
        p
        `(do
           (org.jetbrains.kotlin.cli.jvm.K2JVMCompiler/main
             (into-array ["-cp" (System/getProperty "java.class.path") "-d" ~target ~ksrc])))))))
