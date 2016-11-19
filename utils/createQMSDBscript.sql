--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.2
-- Dumped by pg_dump version 9.2.2
-- Started on 2014-02-21 14:33:25

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE qmsdb;

CREATE ROLE test LOGIN
  ENCRYPTED PASSWORD 'md505a671c66aefea124cc08b76ea6d30bb'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

--
-- TOC entry 2121 (class 1262 OID 17248)
-- Name: qmsdb; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE qmsdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE qmsdb OWNER TO postgres;

\connect qmsdb

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 162531)
-- Name: public; Type: SCHEMA; Schema: -; Owner: test
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO test;

--
-- TOC entry 192 (class 3079 OID 11727)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2122 (class 0 OID 0)
-- Dependencies: 192
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 170 (class 1259 OID 162553)
-- Name: appuser; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE appuser (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    admin boolean,
    email character varying(255),
    locale character varying(255),
    login character varying(255),
    name character varying(255),
    password character varying(255),
    resetpwd boolean,
    surname character varying(255),
    uid character varying(255)
);


ALTER TABLE public.appuser OWNER TO test;

--
-- TOC entry 173 (class 1259 OID 162584)
-- Name: comment; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE comment (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    content text,
    createdate timestamp without time zone,
    uid character varying(255),
    creator_id bigint,
    item_id bigint
);


ALTER TABLE public.comment OWNER TO test;

--
-- TOC entry 168 (class 1259 OID 162533)
-- Name: component; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE component (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    createdate timestamp without time zone,
    description text,
    finalproduct boolean,
    modifydate timestamp without time zone,
    name character varying(255),
    referencenumber character varying(255),
    uid character varying(255),
    createby_id bigint,
    customer_id bigint,
    modifyby_id bigint,
    supplier_id bigint
);


ALTER TABLE public.component OWNER TO test;

--
-- TOC entry 180 (class 1259 OID 162643)
-- Name: component_component; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE component_component (
    component_id bigint NOT NULL,
    relatedcomponents_id bigint NOT NULL
);


ALTER TABLE public.component_component OWNER TO test;

--
-- TOC entry 178 (class 1259 OID 162633)
-- Name: component_fileentity; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE component_fileentity (
    component_id bigint NOT NULL,
    files_id bigint NOT NULL
);


ALTER TABLE public.component_fileentity OWNER TO test;

--
-- TOC entry 175 (class 1259 OID 162602)
-- Name: customer; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE customer (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    address character varying(255),
    contactperson character varying(255),
    email character varying(255),
    name character varying(255),
    telephone character varying(255),
    uid character varying(255)
);


ALTER TABLE public.customer OWNER TO test;

--
-- TOC entry 177 (class 1259 OID 162624)
-- Name: event; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE event (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    allday boolean,
    datefrom timestamp without time zone,
    dateto timestamp without time zone,
    desciption text,
    editable boolean,
    styleclass character varying(255),
    title character varying(255),
    uid character varying(255)
);


ALTER TABLE public.event OWNER TO test;

--
-- TOC entry 190 (class 1259 OID 162691)
-- Name: event_appuser; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE event_appuser (
    event_id bigint NOT NULL,
    hearings_id bigint NOT NULL
);


ALTER TABLE public.event_appuser OWNER TO test;

--
-- TOC entry 169 (class 1259 OID 162544)
-- Name: fileentity; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE fileentity (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    content bytea,
    createdate timestamp without time zone,
    description character varying(255),
    doctype character varying(255),
    filename character varying(255),
    mimetype character varying(255),
    modifydate timestamp without time zone,
    relateduid character varying(255),
    status boolean,
    uid character varying(255),
    createby_id bigint,
    item_id bigint,
    modifyby_id bigint
);


ALTER TABLE public.fileentity OWNER TO test;

--
-- TOC entry 172 (class 1259 OID 162573)
-- Name: gauge; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE gauge (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    createdate timestamp without time zone,
    description text,
    modifydate timestamp without time zone,
    referencenumber character varying(255),
    uid character varying(255),
    validationdate date,
    validationperiod integer,
    createby_id bigint,
    modifyby_id bigint,
    event_id bigint
);


ALTER TABLE public.gauge OWNER TO test;

--
-- TOC entry 185 (class 1259 OID 162666)
-- Name: gauge_component; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE gauge_component (
    gauge_id bigint NOT NULL,
    relatedcomponents_id bigint NOT NULL
);


ALTER TABLE public.gauge_component OWNER TO test;

--
-- TOC entry 184 (class 1259 OID 162661)
-- Name: gauge_fileentity; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE gauge_fileentity (
    gauge_id bigint NOT NULL,
    files_id bigint NOT NULL
);


ALTER TABLE public.gauge_fileentity OWNER TO test;

--
-- TOC entry 176 (class 1259 OID 162611)
-- Name: issue; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE issue (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    createdate timestamp without time zone,
    description text,
    issuenumber bigint,
    issuepriority character varying(255),
    issuestatus character varying(255),
    issuetype character varying(255),
    modifydate timestamp without time zone,
    referencenumber character varying(255),
    resolvedate timestamp without time zone,
    title character varying(255),
    uid character varying(255),
    assigneto_id bigint,
    createby_id bigint,
    modifyby_id bigint
);


ALTER TABLE public.issue OWNER TO test;

--
-- TOC entry 189 (class 1259 OID 162686)
-- Name: issue_component; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE issue_component (
    issue_id bigint NOT NULL,
    relatedcomponents_id bigint NOT NULL
);


ALTER TABLE public.issue_component OWNER TO test;

--
-- TOC entry 186 (class 1259 OID 162671)
-- Name: issue_fileentity; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE issue_fileentity (
    issue_id bigint NOT NULL,
    files_id bigint NOT NULL
);


ALTER TABLE public.issue_fileentity OWNER TO test;

--
-- TOC entry 188 (class 1259 OID 162681)
-- Name: issue_has_labels; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE issue_has_labels (
    issue_id bigint NOT NULL,
    labels_id bigint NOT NULL
);


ALTER TABLE public.issue_has_labels OWNER TO test;

--
-- TOC entry 179 (class 1259 OID 162638)
-- Name: issue_has_relatedcomponents; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE issue_has_relatedcomponents (
    items_id bigint NOT NULL,
    issues_id bigint NOT NULL
);


ALTER TABLE public.issue_has_relatedcomponents OWNER TO test;

--
-- TOC entry 187 (class 1259 OID 162676)
-- Name: issue_issue; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE issue_issue (
    linkedissues_id bigint NOT NULL,
    haslinkedissues_id bigint NOT NULL
);


ALTER TABLE public.issue_issue OWNER TO test;

--
-- TOC entry 181 (class 1259 OID 162648)
-- Name: item; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE item (
    createby_id bigint,
    modifyby_id bigint
);


ALTER TABLE public.item OWNER TO test;

--
-- TOC entry 183 (class 1259 OID 162656)
-- Name: item_component; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE item_component (
    item_id character varying(255) NOT NULL,
    relatedcomponents_id bigint NOT NULL
);


ALTER TABLE public.item_component OWNER TO test;

--
-- TOC entry 182 (class 1259 OID 162651)
-- Name: item_fileentity; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE item_fileentity (
    item_id character varying(255) NOT NULL,
    files_id bigint NOT NULL
);


ALTER TABLE public.item_fileentity OWNER TO test;

--
-- TOC entry 171 (class 1259 OID 162564)
-- Name: label; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE label (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    label character varying(255),
    relatedissuescount integer,
    uid character varying(255)
);


ALTER TABLE public.label OWNER TO test;

--
-- TOC entry 191 (class 1259 OID 162871)
-- Name: sequence; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


ALTER TABLE public.sequence OWNER TO test;

--
-- TOC entry 174 (class 1259 OID 162593)
-- Name: supplier; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE supplier (
    id bigint NOT NULL,
    active boolean DEFAULT true,
    address character varying(255),
    contactperson character varying(255),
    email character varying(255),
    name character varying(255),
    telephone character varying(255),
    uid character varying(255)
);


ALTER TABLE public.supplier OWNER TO test;

--
-- TOC entry 2033 (class 2606 OID 162563)
-- Name: appuser_login_key; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY appuser
    ADD CONSTRAINT appuser_login_key UNIQUE (login);


--
-- TOC entry 2035 (class 2606 OID 162561)
-- Name: appuser_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY appuser
    ADD CONSTRAINT appuser_pkey PRIMARY KEY (id);


--
-- TOC entry 2043 (class 2606 OID 162592)
-- Name: comment_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- TOC entry 2061 (class 2606 OID 162647)
-- Name: component_component_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY component_component
    ADD CONSTRAINT component_component_pkey PRIMARY KEY (component_id, relatedcomponents_id);


--
-- TOC entry 2057 (class 2606 OID 162637)
-- Name: component_fileentity_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY component_fileentity
    ADD CONSTRAINT component_fileentity_pkey PRIMARY KEY (component_id, files_id);


--
-- TOC entry 2027 (class 2606 OID 162541)
-- Name: component_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY component
    ADD CONSTRAINT component_pkey PRIMARY KEY (id);


--
-- TOC entry 2029 (class 2606 OID 162543)
-- Name: component_referencenumber_key; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY component
    ADD CONSTRAINT component_referencenumber_key UNIQUE (referencenumber);


--
-- TOC entry 2047 (class 2606 OID 162610)
-- Name: customer_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 2079 (class 2606 OID 162695)
-- Name: event_appuser_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY event_appuser
    ADD CONSTRAINT event_appuser_pkey PRIMARY KEY (event_id, hearings_id);


--
-- TOC entry 2055 (class 2606 OID 162632)
-- Name: event_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY event
    ADD CONSTRAINT event_pkey PRIMARY KEY (id);


--
-- TOC entry 2031 (class 2606 OID 162552)
-- Name: fileentity_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY fileentity
    ADD CONSTRAINT fileentity_pkey PRIMARY KEY (id);


--
-- TOC entry 2069 (class 2606 OID 162670)
-- Name: gauge_component_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY gauge_component
    ADD CONSTRAINT gauge_component_pkey PRIMARY KEY (gauge_id, relatedcomponents_id);


--
-- TOC entry 2067 (class 2606 OID 162665)
-- Name: gauge_fileentity_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY gauge_fileentity
    ADD CONSTRAINT gauge_fileentity_pkey PRIMARY KEY (gauge_id, files_id);


--
-- TOC entry 2039 (class 2606 OID 162581)
-- Name: gauge_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY gauge
    ADD CONSTRAINT gauge_pkey PRIMARY KEY (id);


--
-- TOC entry 2041 (class 2606 OID 162583)
-- Name: gauge_referencenumber_key; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY gauge
    ADD CONSTRAINT gauge_referencenumber_key UNIQUE (referencenumber);


--
-- TOC entry 2077 (class 2606 OID 162690)
-- Name: issue_component_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue_component
    ADD CONSTRAINT issue_component_pkey PRIMARY KEY (issue_id, relatedcomponents_id);


--
-- TOC entry 2071 (class 2606 OID 162675)
-- Name: issue_fileentity_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue_fileentity
    ADD CONSTRAINT issue_fileentity_pkey PRIMARY KEY (issue_id, files_id);


--
-- TOC entry 2075 (class 2606 OID 162685)
-- Name: issue_has_labels_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue_has_labels
    ADD CONSTRAINT issue_has_labels_pkey PRIMARY KEY (issue_id, labels_id);


--
-- TOC entry 2059 (class 2606 OID 162642)
-- Name: issue_has_relatedcomponents_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue_has_relatedcomponents
    ADD CONSTRAINT issue_has_relatedcomponents_pkey PRIMARY KEY (items_id, issues_id);


--
-- TOC entry 2073 (class 2606 OID 162680)
-- Name: issue_issue_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue_issue
    ADD CONSTRAINT issue_issue_pkey PRIMARY KEY (linkedissues_id, haslinkedissues_id);


--
-- TOC entry 2049 (class 2606 OID 162621)
-- Name: issue_issuenumber_key; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue
    ADD CONSTRAINT issue_issuenumber_key UNIQUE (issuenumber);


--
-- TOC entry 2051 (class 2606 OID 162619)
-- Name: issue_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue
    ADD CONSTRAINT issue_pkey PRIMARY KEY (id);


--
-- TOC entry 2053 (class 2606 OID 162623)
-- Name: issue_referencenumber_key; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY issue
    ADD CONSTRAINT issue_referencenumber_key UNIQUE (referencenumber);


--
-- TOC entry 2065 (class 2606 OID 162660)
-- Name: item_component_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY item_component
    ADD CONSTRAINT item_component_pkey PRIMARY KEY (item_id, relatedcomponents_id);


--
-- TOC entry 2063 (class 2606 OID 162655)
-- Name: item_fileentity_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY item_fileentity
    ADD CONSTRAINT item_fileentity_pkey PRIMARY KEY (item_id, files_id);


--
-- TOC entry 2037 (class 2606 OID 162572)
-- Name: label_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY label
    ADD CONSTRAINT label_pkey PRIMARY KEY (id);


--
-- TOC entry 2081 (class 2606 OID 162875)
-- Name: sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);


--
-- TOC entry 2045 (class 2606 OID 162601)
-- Name: supplier_pkey; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY supplier
    ADD CONSTRAINT supplier_pkey PRIMARY KEY (id);


--
-- TOC entry 2091 (class 2606 OID 162741)
-- Name: fk_comment_creator_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY comment
    ADD CONSTRAINT fk_comment_creator_id FOREIGN KEY (creator_id) REFERENCES appuser(id);


--
-- TOC entry 2099 (class 2606 OID 162781)
-- Name: fk_component_component_component_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component_component
    ADD CONSTRAINT fk_component_component_component_id FOREIGN KEY (component_id) REFERENCES component(id);


--
-- TOC entry 2100 (class 2606 OID 162786)
-- Name: fk_component_component_relatedcomponents_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component_component
    ADD CONSTRAINT fk_component_component_relatedcomponents_id FOREIGN KEY (relatedcomponents_id) REFERENCES component(id);


--
-- TOC entry 2082 (class 2606 OID 162696)
-- Name: fk_component_createby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component
    ADD CONSTRAINT fk_component_createby_id FOREIGN KEY (createby_id) REFERENCES appuser(id);


--
-- TOC entry 2085 (class 2606 OID 162711)
-- Name: fk_component_customer_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component
    ADD CONSTRAINT fk_component_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- TOC entry 2095 (class 2606 OID 162761)
-- Name: fk_component_fileentity_component_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component_fileentity
    ADD CONSTRAINT fk_component_fileentity_component_id FOREIGN KEY (component_id) REFERENCES component(id);


--
-- TOC entry 2096 (class 2606 OID 162766)
-- Name: fk_component_fileentity_files_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component_fileentity
    ADD CONSTRAINT fk_component_fileentity_files_id FOREIGN KEY (files_id) REFERENCES fileentity(id);


--
-- TOC entry 2083 (class 2606 OID 162701)
-- Name: fk_component_modifyby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component
    ADD CONSTRAINT fk_component_modifyby_id FOREIGN KEY (modifyby_id) REFERENCES appuser(id);


--
-- TOC entry 2084 (class 2606 OID 162706)
-- Name: fk_component_supplier_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY component
    ADD CONSTRAINT fk_component_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id);


--
-- TOC entry 2116 (class 2606 OID 162866)
-- Name: fk_event_appuser_event_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY event_appuser
    ADD CONSTRAINT fk_event_appuser_event_id FOREIGN KEY (event_id) REFERENCES event(id);


--
-- TOC entry 2115 (class 2606 OID 162861)
-- Name: fk_event_appuser_hearings_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY event_appuser
    ADD CONSTRAINT fk_event_appuser_hearings_id FOREIGN KEY (hearings_id) REFERENCES appuser(id);


--
-- TOC entry 2087 (class 2606 OID 162721)
-- Name: fk_fileentity_createby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY fileentity
    ADD CONSTRAINT fk_fileentity_createby_id FOREIGN KEY (createby_id) REFERENCES appuser(id);


--
-- TOC entry 2086 (class 2606 OID 162716)
-- Name: fk_fileentity_modifyby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY fileentity
    ADD CONSTRAINT fk_fileentity_modifyby_id FOREIGN KEY (modifyby_id) REFERENCES appuser(id);


--
-- TOC entry 2105 (class 2606 OID 162811)
-- Name: fk_gauge_component_gauge_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY gauge_component
    ADD CONSTRAINT fk_gauge_component_gauge_id FOREIGN KEY (gauge_id) REFERENCES gauge(id);


--
-- TOC entry 2106 (class 2606 OID 162816)
-- Name: fk_gauge_component_relatedcomponents_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY gauge_component
    ADD CONSTRAINT fk_gauge_component_relatedcomponents_id FOREIGN KEY (relatedcomponents_id) REFERENCES component(id);


--
-- TOC entry 2090 (class 2606 OID 162736)
-- Name: fk_gauge_createby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY gauge
    ADD CONSTRAINT fk_gauge_createby_id FOREIGN KEY (createby_id) REFERENCES appuser(id);


--
-- TOC entry 2088 (class 2606 OID 162726)
-- Name: fk_gauge_event_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY gauge
    ADD CONSTRAINT fk_gauge_event_id FOREIGN KEY (event_id) REFERENCES event(id);


--
-- TOC entry 2103 (class 2606 OID 162801)
-- Name: fk_gauge_fileentity_files_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY gauge_fileentity
    ADD CONSTRAINT fk_gauge_fileentity_files_id FOREIGN KEY (files_id) REFERENCES fileentity(id);


--
-- TOC entry 2104 (class 2606 OID 162806)
-- Name: fk_gauge_fileentity_gauge_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY gauge_fileentity
    ADD CONSTRAINT fk_gauge_fileentity_gauge_id FOREIGN KEY (gauge_id) REFERENCES gauge(id);


--
-- TOC entry 2089 (class 2606 OID 162731)
-- Name: fk_gauge_modifyby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY gauge
    ADD CONSTRAINT fk_gauge_modifyby_id FOREIGN KEY (modifyby_id) REFERENCES appuser(id);


--
-- TOC entry 2094 (class 2606 OID 162756)
-- Name: fk_issue_assigneto_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue
    ADD CONSTRAINT fk_issue_assigneto_id FOREIGN KEY (assigneto_id) REFERENCES appuser(id);


--
-- TOC entry 2113 (class 2606 OID 162851)
-- Name: fk_issue_component_issue_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_component
    ADD CONSTRAINT fk_issue_component_issue_id FOREIGN KEY (issue_id) REFERENCES issue(id);


--
-- TOC entry 2114 (class 2606 OID 162856)
-- Name: fk_issue_component_relatedcomponents_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_component
    ADD CONSTRAINT fk_issue_component_relatedcomponents_id FOREIGN KEY (relatedcomponents_id) REFERENCES component(id);


--
-- TOC entry 2092 (class 2606 OID 162746)
-- Name: fk_issue_createby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue
    ADD CONSTRAINT fk_issue_createby_id FOREIGN KEY (createby_id) REFERENCES appuser(id);


--
-- TOC entry 2107 (class 2606 OID 162821)
-- Name: fk_issue_fileentity_files_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_fileentity
    ADD CONSTRAINT fk_issue_fileentity_files_id FOREIGN KEY (files_id) REFERENCES fileentity(id);


--
-- TOC entry 2108 (class 2606 OID 162826)
-- Name: fk_issue_fileentity_issue_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_fileentity
    ADD CONSTRAINT fk_issue_fileentity_issue_id FOREIGN KEY (issue_id) REFERENCES issue(id);


--
-- TOC entry 2112 (class 2606 OID 162846)
-- Name: fk_issue_has_labels_issue_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_has_labels
    ADD CONSTRAINT fk_issue_has_labels_issue_id FOREIGN KEY (issue_id) REFERENCES issue(id);


--
-- TOC entry 2111 (class 2606 OID 162841)
-- Name: fk_issue_has_labels_labels_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_has_labels
    ADD CONSTRAINT fk_issue_has_labels_labels_id FOREIGN KEY (labels_id) REFERENCES label(id);


--
-- TOC entry 2097 (class 2606 OID 162771)
-- Name: fk_issue_has_relatedcomponents_issues_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_has_relatedcomponents
    ADD CONSTRAINT fk_issue_has_relatedcomponents_issues_id FOREIGN KEY (issues_id) REFERENCES issue(id);


--
-- TOC entry 2098 (class 2606 OID 162776)
-- Name: fk_issue_has_relatedcomponents_items_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_has_relatedcomponents
    ADD CONSTRAINT fk_issue_has_relatedcomponents_items_id FOREIGN KEY (items_id) REFERENCES component(id);


--
-- TOC entry 2109 (class 2606 OID 162831)
-- Name: fk_issue_issue_haslinkedissues_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_issue
    ADD CONSTRAINT fk_issue_issue_haslinkedissues_id FOREIGN KEY (haslinkedissues_id) REFERENCES issue(id);


--
-- TOC entry 2110 (class 2606 OID 162836)
-- Name: fk_issue_issue_linkedissues_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue_issue
    ADD CONSTRAINT fk_issue_issue_linkedissues_id FOREIGN KEY (linkedissues_id) REFERENCES issue(id);


--
-- TOC entry 2093 (class 2606 OID 162751)
-- Name: fk_issue_modifyby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY issue
    ADD CONSTRAINT fk_issue_modifyby_id FOREIGN KEY (modifyby_id) REFERENCES appuser(id);


--
-- TOC entry 2102 (class 2606 OID 162796)
-- Name: fk_item_createby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_createby_id FOREIGN KEY (createby_id) REFERENCES appuser(id);


--
-- TOC entry 2101 (class 2606 OID 162791)
-- Name: fk_item_modifyby_id; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_item_modifyby_id FOREIGN KEY (modifyby_id) REFERENCES appuser(id);


-- Completed on 2014-02-21 14:33:25

--
-- PostgreSQL database dump complete
--

