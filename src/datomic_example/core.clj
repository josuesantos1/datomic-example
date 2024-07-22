(ns datomic-example.core
  (:require [datomic.api :as d]))

;; datomic uri in-memory
(def uri "datomic:mem://datomic-example")

;; cria o banco
(d/create-database uri)

;; connect no banco
(def datomic (d/connect uri))

;; cria um schema simples
(def schema
  [{:db/ident       :person/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "A person's name"}
   {:db/ident       :person/email
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "A person's email"}
   {:db/ident       :person/password
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "A person's password"}])

;; grava o schema no banco
(d/transact datomic schema)

;; um exemplo de entidade person
(def person
  [{:person/name "Josue"
    :person/email "josue@test.mail"
    :person/password "senhasupersecreta123"}])

;; grava person no banco
(d/transact datomic person)

;; faz uma busca no banco
(d/pull (d/db datomic) '[*] [:person/email "josue@test.mail"])

