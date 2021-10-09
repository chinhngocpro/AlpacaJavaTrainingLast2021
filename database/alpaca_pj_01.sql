--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2021-10-09 20:50:21

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE alpaca_pj_01;
--
-- TOC entry 3181 (class 1262 OID 16739)
-- Name: alpaca_pj_01; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE alpaca_pj_01 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Vietnamese_Vietnam.1258';


ALTER DATABASE alpaca_pj_01 OWNER TO postgres;

\connect alpaca_pj_01

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 200 (class 1259 OID 16740)
-- Name: analyzed_receipts; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.analyzed_receipts (
    id integer NOT NULL,
    accident_id integer NOT NULL,
    amount double precision NOT NULL,
    hospital_id integer NOT NULL,
    is_valid boolean NOT NULL,
    title character varying(255) NOT NULL,
    analyzer_id integer,
    request_id integer
);


ALTER TABLE public.analyzed_receipts OWNER TO alpaca;

--
-- TOC entry 217 (class 1259 OID 16904)
-- Name: analyzed_receipts_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.analyzed_receipts_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    accident_id integer,
    amount double precision,
    hospital_id integer,
    is_valid boolean,
    title character varying(255),
    analyzer_id integer,
    request_id integer
);


ALTER TABLE public.analyzed_receipts_aud OWNER TO alpaca;

--
-- TOC entry 208 (class 1259 OID 16844)
-- Name: analyzed_receipts_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.analyzed_receipts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.analyzed_receipts_id_seq OWNER TO alpaca;

--
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 208
-- Name: analyzed_receipts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.analyzed_receipts_id_seq OWNED BY public.analyzed_receipts.id;


--
-- TOC entry 215 (class 1259 OID 16865)
-- Name: authorities; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.authorities (
    id integer NOT NULL,
    permission_name character varying(50) NOT NULL
);


ALTER TABLE public.authorities OWNER TO alpaca;

--
-- TOC entry 218 (class 1259 OID 16909)
-- Name: authorities_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.authorities_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    permission_name character varying(255)
);


ALTER TABLE public.authorities_aud OWNER TO alpaca;

--
-- TOC entry 214 (class 1259 OID 16863)
-- Name: authorities_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.authorities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.authorities_id_seq OWNER TO alpaca;

--
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 214
-- Name: authorities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.authorities_id_seq OWNED BY public.authorities.id;


--
-- TOC entry 201 (class 1259 OID 16745)
-- Name: claim_requests; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.claim_requests (
    id integer NOT NULL,
    description character varying(255),
    title character varying(255),
    receipt_photos text[],
    status character varying(255),
    customer_id integer,
    employee_id integer,
    employee_in_charge bytea
);


ALTER TABLE public.claim_requests OWNER TO alpaca;

--
-- TOC entry 219 (class 1259 OID 16914)
-- Name: claim_requests_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.claim_requests_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    description character varying(255),
    receipt_photos text[],
    status character varying(255),
    title character varying(255),
    customer_id integer,
    employee_id integer,
    employee_in_charge bytea
);


ALTER TABLE public.claim_requests_aud OWNER TO alpaca;

--
-- TOC entry 209 (class 1259 OID 16847)
-- Name: claim_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.claim_requests_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.claim_requests_id_seq OWNER TO alpaca;

--
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 209
-- Name: claim_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.claim_requests_id_seq OWNED BY public.claim_requests.id;


--
-- TOC entry 202 (class 1259 OID 16753)
-- Name: contracts; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.contracts (
    id integer NOT NULL,
    acceptable_accident_ids integer[],
    acceptable_hospital_ids integer[],
    active boolean NOT NULL,
    contract_code character(12) NOT NULL,
    maximum_amount double precision NOT NULL,
    remaining_amount double precision NOT NULL,
    valid_from timestamp without time zone NOT NULL,
    valid_to timestamp without time zone NOT NULL,
    customer_id integer
);


ALTER TABLE public.contracts OWNER TO alpaca;

--
-- TOC entry 220 (class 1259 OID 16922)
-- Name: contracts_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.contracts_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    acceptable_accident_ids integer[],
    acceptable_hospital_ids integer[],
    active boolean,
    contract_code character varying(255),
    maximum_amount double precision,
    remaining_amount double precision,
    valid_from timestamp without time zone,
    valid_to timestamp without time zone,
    customer_id integer
);


ALTER TABLE public.contracts_aud OWNER TO alpaca;

--
-- TOC entry 210 (class 1259 OID 16850)
-- Name: contracts_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.contracts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contracts_id_seq OWNER TO alpaca;

--
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 210
-- Name: contracts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.contracts_id_seq OWNED BY public.contracts.id;


--
-- TOC entry 203 (class 1259 OID 16761)
-- Name: customers; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.customers (
    id integer NOT NULL,
    active boolean NOT NULL,
    address character varying(255) NOT NULL,
    date_of_birth date NOT NULL,
    email character varying(255) NOT NULL,
    full_name character varying(255) NOT NULL,
    gender boolean NOT NULL,
    id_card_number character varying(255) NOT NULL,
    occupation character varying(255),
    phone_numbers text[] NOT NULL
);


ALTER TABLE public.customers OWNER TO alpaca;

--
-- TOC entry 221 (class 1259 OID 16930)
-- Name: customers_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.customers_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    active boolean,
    address character varying(255),
    date_of_birth date,
    email character varying(255),
    full_name character varying(255),
    gender boolean,
    id_card_number character varying(255),
    occupation character varying(255),
    phone_numbers text[]
);


ALTER TABLE public.customers_aud OWNER TO alpaca;

--
-- TOC entry 211 (class 1259 OID 16853)
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.customers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customers_id_seq OWNER TO alpaca;

--
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 211
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.customers_id_seq OWNED BY public.customers.id;


--
-- TOC entry 227 (class 1259 OID 16966)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO alpaca;

--
-- TOC entry 204 (class 1259 OID 16769)
-- Name: payments; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.payments (
    id integer NOT NULL,
    amount double precision NOT NULL,
    payment_date timestamp without time zone NOT NULL,
    accountant_id integer,
    request_id integer
);


ALTER TABLE public.payments OWNER TO alpaca;

--
-- TOC entry 222 (class 1259 OID 16938)
-- Name: payments_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.payments_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    amount double precision,
    payment_date timestamp without time zone,
    accountant_id integer,
    request_id integer
);


ALTER TABLE public.payments_aud OWNER TO alpaca;

--
-- TOC entry 212 (class 1259 OID 16856)
-- Name: payments_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.payments_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.payments_id_seq OWNER TO alpaca;

--
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 212
-- Name: payments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.payments_id_seq OWNED BY public.payments.id;


--
-- TOC entry 223 (class 1259 OID 16943)
-- Name: revinfo; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.revinfo (
    rev integer NOT NULL,
    revtstmp bigint
);


ALTER TABLE public.revinfo OWNER TO alpaca;

--
-- TOC entry 205 (class 1259 OID 16774)
-- Name: roles; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.roles OWNER TO alpaca;

--
-- TOC entry 224 (class 1259 OID 16948)
-- Name: roles_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.roles_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    name character varying(255)
);


ALTER TABLE public.roles_aud OWNER TO alpaca;

--
-- TOC entry 216 (class 1259 OID 16872)
-- Name: roles_authorities; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.roles_authorities (
    role_id integer NOT NULL,
    authority_id integer NOT NULL
);


ALTER TABLE public.roles_authorities OWNER TO alpaca;

--
-- TOC entry 225 (class 1259 OID 16953)
-- Name: roles_authorities_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.roles_authorities_aud (
    rev integer NOT NULL,
    authority_id integer NOT NULL,
    role_id integer NOT NULL,
    revtype smallint
);


ALTER TABLE public.roles_authorities_aud OWNER TO alpaca;

--
-- TOC entry 207 (class 1259 OID 16841)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO alpaca;

--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 207
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 206 (class 1259 OID 16779)
-- Name: users; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.users (
    id integer NOT NULL,
    active boolean NOT NULL,
    address character varying(255) NOT NULL,
    date_of_birth date NOT NULL,
    email character varying(255) NOT NULL,
    full_name character varying(255),
    gender boolean NOT NULL,
    id_card_number character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone_numbers text[] NOT NULL,
    username character varying(255) NOT NULL,
    role_id integer
);


ALTER TABLE public.users OWNER TO alpaca;

--
-- TOC entry 226 (class 1259 OID 16958)
-- Name: users_aud; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.users_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    active boolean,
    address character varying(255),
    date_of_birth date,
    email character varying(255),
    full_name character varying(255),
    gender boolean,
    id_card_number character varying(255),
    password character varying(255),
    phone_numbers text[],
    username character varying(255),
    role_id integer
);


ALTER TABLE public.users_aud OWNER TO alpaca;

--
-- TOC entry 213 (class 1259 OID 16859)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO alpaca;

--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 213
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 2946 (class 2604 OID 16896)
-- Name: analyzed_receipts id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.analyzed_receipts ALTER COLUMN id SET DEFAULT nextval('public.analyzed_receipts_id_seq'::regclass);


--
-- TOC entry 2953 (class 2604 OID 16897)
-- Name: authorities id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.authorities ALTER COLUMN id SET DEFAULT nextval('public.authorities_id_seq'::regclass);


--
-- TOC entry 2947 (class 2604 OID 16898)
-- Name: claim_requests id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.claim_requests ALTER COLUMN id SET DEFAULT nextval('public.claim_requests_id_seq'::regclass);


--
-- TOC entry 2948 (class 2604 OID 16899)
-- Name: contracts id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts ALTER COLUMN id SET DEFAULT nextval('public.contracts_id_seq'::regclass);


--
-- TOC entry 2949 (class 2604 OID 16900)
-- Name: customers id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customers_id_seq'::regclass);


--
-- TOC entry 2950 (class 2604 OID 16901)
-- Name: payments id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.payments ALTER COLUMN id SET DEFAULT nextval('public.payments_id_seq'::regclass);


--
-- TOC entry 2951 (class 2604 OID 16902)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 2952 (class 2604 OID 16903)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3148 (class 0 OID 16740)
-- Dependencies: 200
-- Data for Name: analyzed_receipts; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3165 (class 0 OID 16904)
-- Dependencies: 217
-- Data for Name: analyzed_receipts_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3163 (class 0 OID 16865)
-- Dependencies: 215
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.authorities (id, permission_name) VALUES (1, 'USER_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (2, 'USER_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (3, 'USER_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (4, 'USER_DELETE');
INSERT INTO public.authorities (id, permission_name) VALUES (9, 'AUTHORITY_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (10, 'AUTHORITY_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (11, 'AUTHORITY_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (12, 'AUTHORITY_DELETE');
INSERT INTO public.authorities (id, permission_name) VALUES (13, 'CUSTOMER_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (14, 'CUSTOMER_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (15, 'CUSTOMER_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (16, 'CUSTOMER_DELETE');
INSERT INTO public.authorities (id, permission_name) VALUES (17, 'CLAIM_REQUEST_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (18, 'CLAIM_REQUEST_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (19, 'CLAIM_REQUEST_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (20, 'CLAIM_REQUEST_DELETE');
INSERT INTO public.authorities (id, permission_name) VALUES (21, 'ANALYZED_RECEIPT_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (22, 'ANALYZED_RECEIPT_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (23, 'ANALYZED_RECEIPT_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (24, 'ANALYZED_RECEIPT_DELETE');
INSERT INTO public.authorities (id, permission_name) VALUES (25, 'CONTRACT_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (26, 'CONTRACT_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (27, 'CONTRACT_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (28, 'CONTRACT_DELETE');
INSERT INTO public.authorities (id, permission_name) VALUES (29, 'PAYMENT_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (30, 'PAYMENT_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (31, 'PAYMENT_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (32, 'PAYMENT_DELETE');
INSERT INTO public.authorities (id, permission_name) VALUES (5, 'SYSTEM_ROLE_READ');
INSERT INTO public.authorities (id, permission_name) VALUES (6, 'SYSTEM_ROLE_CREATE');
INSERT INTO public.authorities (id, permission_name) VALUES (7, 'SYSTEM_ROLE_UPDATE');
INSERT INTO public.authorities (id, permission_name) VALUES (8, 'SYSTEM_ROLE_DELETE');


--
-- TOC entry 3166 (class 0 OID 16909)
-- Dependencies: 218
-- Data for Name: authorities_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3149 (class 0 OID 16745)
-- Dependencies: 201
-- Data for Name: claim_requests; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.claim_requests (id, description, title, receipt_photos, status, customer_id, employee_id, employee_in_charge) VALUES (1, 'Description Placeholder', 'Title Placeholder', '{}', 'PENDING', NULL, NULL, NULL);
INSERT INTO public.claim_requests (id, description, title, receipt_photos, status, customer_id, employee_id, employee_in_charge) VALUES (2, 'Description Placeholder', 'Title Placeholder', '{}', 'PENDING', NULL, NULL, NULL);
INSERT INTO public.claim_requests (id, description, title, receipt_photos, status, customer_id, employee_id, employee_in_charge) VALUES (3, 'Description Placeholder', 'Title Placeholder', '{c768b7d0-ca65-4d20-af95-48337a84cfbe.jpg,a114a8be-6c50-4bf9-84a4-7cbc634f64a8.jpg,f3262d0e-5be0-4fb6-911d-afd07054b72b.jpg}', 'PENDING', NULL, NULL, NULL);
INSERT INTO public.claim_requests (id, description, title, receipt_photos, status, customer_id, employee_id, employee_in_charge) VALUES (4, 'Description Placeholder', 'Title Placeholder', '{1eb60440-15fd-4bb3-84ec-a13f6f68498f.jpg,e1e55ce2-bc63-4b07-8cd8-71be3cae8970.jpg,85b3340e-8bd3-4b16-a47a-fa1b15288911.jpg,7baae55c-e53e-4cb6-adf5-b21e0af6aeb9.jpg}', 'PENDING', 3, NULL, NULL);
INSERT INTO public.claim_requests (id, description, title, receipt_photos, status, customer_id, employee_id, employee_in_charge) VALUES (5, 'đâsdas,dsadasdasd', 'ádasdasda,Title placeholder', '{7de79702-7424-40f4-9057-25717f19767c.jpg,8b6133a8-c239-48ca-b54b-e5d065c6d118.jpg,6273c5dd-3089-4088-9e62-19448bac4638.jpg,589aad60-989a-4556-b36e-ea14701a9fc8.jpg,33667edd-79dc-4226-9604-20ad75cfb26a.jfif}', 'PENDING', 3, NULL, NULL);


--
-- TOC entry 3167 (class 0 OID 16914)
-- Dependencies: 219
-- Data for Name: claim_requests_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.claim_requests_aud (id, rev, revtype, description, receipt_photos, status, title, customer_id, employee_id, employee_in_charge) VALUES (1, 24, 0, 'Description Placeholder', '{}', 'PENDING', 'Title Placeholder', NULL, NULL, NULL);
INSERT INTO public.claim_requests_aud (id, rev, revtype, description, receipt_photos, status, title, customer_id, employee_id, employee_in_charge) VALUES (2, 25, 0, 'Description Placeholder', '{}', 'PENDING', 'Title Placeholder', NULL, NULL, NULL);
INSERT INTO public.claim_requests_aud (id, rev, revtype, description, receipt_photos, status, title, customer_id, employee_id, employee_in_charge) VALUES (3, 26, 0, 'Description Placeholder', '{c768b7d0-ca65-4d20-af95-48337a84cfbe.jpg,a114a8be-6c50-4bf9-84a4-7cbc634f64a8.jpg,f3262d0e-5be0-4fb6-911d-afd07054b72b.jpg}', 'PENDING', 'Title Placeholder', NULL, NULL, NULL);
INSERT INTO public.claim_requests_aud (id, rev, revtype, description, receipt_photos, status, title, customer_id, employee_id, employee_in_charge) VALUES (4, 29, 0, 'Description Placeholder', '{1eb60440-15fd-4bb3-84ec-a13f6f68498f.jpg,e1e55ce2-bc63-4b07-8cd8-71be3cae8970.jpg,85b3340e-8bd3-4b16-a47a-fa1b15288911.jpg,7baae55c-e53e-4cb6-adf5-b21e0af6aeb9.jpg}', 'PENDING', 'Title Placeholder', 3, NULL, NULL);
INSERT INTO public.claim_requests_aud (id, rev, revtype, description, receipt_photos, status, title, customer_id, employee_id, employee_in_charge) VALUES (4, 30, 1, 'Description Placeholder', '{1eb60440-15fd-4bb3-84ec-a13f6f68498f.jpg,e1e55ce2-bc63-4b07-8cd8-71be3cae8970.jpg,85b3340e-8bd3-4b16-a47a-fa1b15288911.jpg,7baae55c-e53e-4cb6-adf5-b21e0af6aeb9.jpg}', 'PENDING', 'Title Placeholder', 3, NULL, NULL);
INSERT INTO public.claim_requests_aud (id, rev, revtype, description, receipt_photos, status, title, customer_id, employee_id, employee_in_charge) VALUES (4, 31, 1, 'Description Placeholder', '{1eb60440-15fd-4bb3-84ec-a13f6f68498f.jpg,e1e55ce2-bc63-4b07-8cd8-71be3cae8970.jpg,85b3340e-8bd3-4b16-a47a-fa1b15288911.jpg,7baae55c-e53e-4cb6-adf5-b21e0af6aeb9.jpg}', 'PENDING', 'Title Placeholder', 3, NULL, NULL);
INSERT INTO public.claim_requests_aud (id, rev, revtype, description, receipt_photos, status, title, customer_id, employee_id, employee_in_charge) VALUES (5, 32, 0, 'đâsdas,dsadasdasd', '{7de79702-7424-40f4-9057-25717f19767c.jpg,8b6133a8-c239-48ca-b54b-e5d065c6d118.jpg,6273c5dd-3089-4088-9e62-19448bac4638.jpg,589aad60-989a-4556-b36e-ea14701a9fc8.jpg,33667edd-79dc-4226-9604-20ad75cfb26a.jfif}', 'PENDING', 'ádasdasda,Title placeholder', 3, NULL, NULL);


--
-- TOC entry 3150 (class 0 OID 16753)
-- Dependencies: 202
-- Data for Name: contracts; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.contracts (id, acceptable_accident_ids, acceptable_hospital_ids, active, contract_code, maximum_amount, remaining_amount, valid_from, valid_to, customer_id) VALUES (3, '{1,2,3,4}', '{1,2,3}', true, 'HDV123456789', 5000000, 1000000, '1992-12-15 16:35:47.483', '2030-10-30 07:49:34.471', 2);
INSERT INTO public.contracts (id, acceptable_accident_ids, acceptable_hospital_ids, active, contract_code, maximum_amount, remaining_amount, valid_from, valid_to, customer_id) VALUES (2, '{3,4}', '{1,2}', true, 'HDK789456123', 5000000, 200000, '1991-12-08 18:31:21.316', '2015-08-14 06:26:27.491', 3);


--
-- TOC entry 3168 (class 0 OID 16922)
-- Dependencies: 220
-- Data for Name: contracts_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.contracts_aud (id, rev, revtype, acceptable_accident_ids, acceptable_hospital_ids, active, contract_code, maximum_amount, remaining_amount, valid_from, valid_to, customer_id) VALUES (2, 21, 0, '{1,2,3,4}', '{1,2,3}', true, 'HDV123456789', 5000000, 1000000, '1992-12-15 16:35:47.483', '2030-10-30 07:49:34.471', 2);
INSERT INTO public.contracts_aud (id, rev, revtype, acceptable_accident_ids, acceptable_hospital_ids, active, contract_code, maximum_amount, remaining_amount, valid_from, valid_to, customer_id) VALUES (3, 22, 0, '{1,2,3,4}', '{1,2,3}', true, 'HDV123456789', 5000000, 1000000, '1992-12-15 16:35:47.483', '2030-10-30 07:49:34.471', 2);
INSERT INTO public.contracts_aud (id, rev, revtype, acceptable_accident_ids, acceptable_hospital_ids, active, contract_code, maximum_amount, remaining_amount, valid_from, valid_to, customer_id) VALUES (2, 23, 1, '{3,4}', '{1,2}', true, 'HDK789456123', 5000000, 200000, '1991-12-08 18:31:21.316', '2015-08-14 06:26:27.491', 3);


--
-- TOC entry 3151 (class 0 OID 16761)
-- Dependencies: 203
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (3, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (10, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456783', 'Student', '{123456789,123456789}');
INSERT INTO public.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (1, false, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456786', 'Student', '{123456789,123456789}');
INSERT INTO public.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (4, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456788', 'Student', '{123456789,123456789}');
INSERT INTO public.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (6, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456785', 'Student', '{123456789,123456789}');
INSERT INTO public.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (2, true, 'Updated address placeholder', '1978-12-11', 'updated_email@gmail.com', 'Updated full name placeholder', false, '123456787', 'Student', '{123456789,123456789}');
INSERT INTO public.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (8, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456784', 'Student', '{123456789,123456789}');


--
-- TOC entry 3169 (class 0 OID 16930)
-- Dependencies: 221
-- Data for Name: customers_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (1, 12, 0, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (2, 13, 0, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (3, 14, 0, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (4, 15, 0, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (2, 16, 1, true, 'Updated address placeholder', '1978-12-11', 'updated_email@gmail.com', 'Updated full name placeholder', false, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (1, 17, 1, false, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (6, 18, 0, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (8, 19, 0, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (10, 20, 0, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (2, 21, 1, true, 'Updated address placeholder', '1978-12-11', 'updated_email@gmail.com', 'Updated full name placeholder', false, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (2, 22, 1, true, 'Updated address placeholder', '1978-12-11', 'updated_email@gmail.com', 'Updated full name placeholder', false, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (3, 23, 1, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (2, 23, 1, true, 'Updated address placeholder', '1978-12-11', 'updated_email@gmail.com', 'Updated full name placeholder', false, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (3, 29, 1, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');
INSERT INTO public.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) VALUES (3, 32, 1, true, 'Address placeholder', '1978-12-11', 'email@gmail.com', 'Full name placeholder', true, '123456789', 'Student', '{123456789,123456789}');


--
-- TOC entry 3152 (class 0 OID 16769)
-- Dependencies: 204
-- Data for Name: payments; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.payments (id, amount, payment_date, accountant_id, request_id) VALUES (1, 500000, '2021-09-17 02:10:45.717', 1, 4);
INSERT INTO public.payments (id, amount, payment_date, accountant_id, request_id) VALUES (2, 500000, '2021-09-17 02:10:45.717', 1, 4);


--
-- TOC entry 3170 (class 0 OID 16938)
-- Dependencies: 222
-- Data for Name: payments_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.payments_aud (id, rev, revtype, amount, payment_date, accountant_id, request_id) VALUES (1, 30, 0, 500000, '2021-09-17 02:10:45.717', 1, 4);
INSERT INTO public.payments_aud (id, rev, revtype, amount, payment_date, accountant_id, request_id) VALUES (2, 31, 0, 500000, '2021-09-17 02:10:45.717', 1, 4);


--
-- TOC entry 3171 (class 0 OID 16943)
-- Dependencies: 223
-- Data for Name: revinfo; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.revinfo (rev, revtstmp) VALUES (1, 1633575354052);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (2, 1633575469068);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (3, 1633576824501);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (4, 1633576887113);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (5, 1633577460847);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (6, 1633577541665);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (7, 1633577715866);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (8, 1633578149676);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (9, 1633582721304);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (10, 1633583614569);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (11, 1633583645355);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (12, 1633585664799);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (13, 1633585764282);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (14, 1633585766725);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (15, 1633585767467);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (16, 1633585920221);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (17, 1633586623718);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (18, 1633587434580);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (19, 1633587525341);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (20, 1633587540891);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (21, 1633597515617);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (22, 1633597792353);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (23, 1633599250348);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (24, 1633600788994);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (25, 1633601035733);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (26, 1633602016101);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (27, 1633666911663);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (28, 1633667960286);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (29, 1633678660531);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (30, 1633678863859);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (31, 1633678877631);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (32, 1633686838096);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (33, 1633763018156);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (34, 1633766772932);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (35, 1633775110690);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (36, 1633778715495);
INSERT INTO public.revinfo (rev, revtstmp) VALUES (37, 1633778737631);


--
-- TOC entry 3153 (class 0 OID 16774)
-- Dependencies: 205
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'EMPLOYEE');
INSERT INTO public.roles (id, name) VALUES (4, 'ACCOUNTANT');
INSERT INTO public.roles (id, name) VALUES (3, 'ANALYZER');


--
-- TOC entry 3172 (class 0 OID 16948)
-- Dependencies: 224
-- Data for Name: roles_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (1, 6, 1, 'ADMIN');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (1, 7, 1, 'ADMIN');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (1, 8, 1, 'ADMIN');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (1, 9, 1, 'ADMIN');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (3, 27, 1, 'ANALYZER');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (2, 28, 1, 'EMPLOYEE');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (1, 28, 1, 'ADMIN');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (2, 33, 1, 'EMPLOYEE');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (2, 34, 1, 'EMPLOYEE');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (3, 35, 1, 'ANALYZER');
INSERT INTO public.roles_aud (id, rev, revtype, name) VALUES (2, 35, 1, 'EMPLOYEE');


--
-- TOC entry 3164 (class 0 OID 16872)
-- Dependencies: 216
-- Data for Name: roles_authorities; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 1);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 2);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 3);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 4);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 5);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 6);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 7);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 8);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 9);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 10);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 11);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 12);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 13);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 14);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 15);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 16);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 17);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 18);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 19);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 20);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 21);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 22);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 23);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 24);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 25);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 26);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 27);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 28);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 29);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 30);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 31);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (1, 32);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (2, 13);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (2, 14);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (2, 17);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (2, 19);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (2, 21);
INSERT INTO public.roles_authorities (role_id, authority_id) VALUES (2, 25);


--
-- TOC entry 3173 (class 0 OID 16953)
-- Dependencies: 225
-- Data for Name: roles_authorities_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3154 (class 0 OID 16779)
-- Dependencies: 206
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.users (id, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (1, true, 'Address placeholder', '2000-01-01', 'emailplaceholder@email.com', 'Admin', true, '123456789', '$2a$10$Sa5.ZqvWQEstDncDJfOdeerazovzTPe6N2pPGIctXQ3wL.AJD9Q76', '{123456789}', 'alpaca_admin', 1);
INSERT INTO public.users (id, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (25, true, 'velit dolor', '1988-10-03', 'mollit sunt commodo Excepteur', 'cupidatat dolore consequat ea', true, 'minim', '$2a$10$MMr1/qnejxE5lpEUQsw/6u4n/Ahlh7uGCD4dNu6r0/J5VUP1h55Ge', '{"sed pariatur",incididunt}', 'fugiat', 2);
INSERT INTO public.users (id, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (30, false, 'velit dolor', '1988-10-03', 'mol@', 'cupidatat dolore consequat ea', true, 'minim4545', '$2a$10$BVI5LFIif/kXA9PF46qTJe84L1ptFVdwsAl4YVvYZKevqGY7ZvOKu', '{"sed pariatur",incididunt}', 'fugiat23', 2);
INSERT INTO public.users (id, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (18, false, 'velit dolor', '1988-10-03', 'chienkieu@fpt.edu.vn', 'fullnamene', true, '123456789756', '$2a$10$ueul6cEZdO3cI5axMQSq4.auiN3liQpv9pORgR05Ir9XU4betZp8e', '{123456789,785611461}', 'user1818', 3);
INSERT INTO public.users (id, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (24, true, 'Excepteur', '1999-03-30', 'email123@gmail.com.vn', 'Fullname Placehodler', true, '798546123', '$2a$10$6bC548ZWWzntooE1/aGN0OOtrZYoVDYsgAMDQTtm0NIZBensxKFv6', '{123456789}', 'user03', 3);


--
-- TOC entry 3174 (class 0 OID 16958)
-- Dependencies: 226
-- Data for Name: users_aud; Type: TABLE DATA; Schema: public; Owner: alpaca
--

INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (8, 1, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$eJ6vmRB2uYSCVROfTnN1m.38aR3Wl6lfyNXT9lJy2ijyt9JNx9fpS', '{0812638042}', 'chien.kieu', NULL);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (9, 2, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$EhchSWf2Zsfs4S2whoN9VeJUVp9d04B3GVnNEt5IjnpmroN5D2sW.', '{0812638042}', 'chien.kieu', NULL);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (11, 3, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$avNIh0Lt3T5HvBj72TAryeSmWwF5MNp7qAZ5scRNC0Sl59RT2.kAe', '{0812638042}', 'chien.kieu', NULL);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (12, 4, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$tt.peLGUUQlcIPMABxjKdO1IUCGPPa4k2io4obd2iskB7cGKftDsO', '{0812638042}', 'chien.kieu', NULL);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (13, 5, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$Src8p/W97JVFXIpZtk.CFOVCRcjuuObfOVQP176ayYtzePcCpaJ0i', '{0812638042}', 'chien.kieu', NULL);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (14, 6, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$Fr.47nxOfhOr2KsHcfkysufFgGTDhVzbSDQntV8JH6Sd9i1wJZwjG', '{0812638042}', 'chien.kieu', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (15, 7, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$Jwiahqa3/IatPc0TpjrE2.hjgKz1I1zZQ8Y6CZ5zU0Yqyi2aTRLzq', '{0812638042}', 'chien.kieu', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (16, 8, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$t6wlv.W83M8IwOw3zYvvteLbNjXmafmIQHJz8ntbRn3EgewpfaGq.', '{0812638042}', 'chien.kieu', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (18, 9, 0, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$rWex4ffLHs1nxMdc5.s7aOgX5alLsGhLtf80hyyn8XJ7lD1SXexay', '{0812638042}', 'chien.kieu', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (18, 10, 1, true, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$DBvsRNy3kM.2UIL8B.bX6OCoTziH201tCeovwFNYBZk8ohu9re5iW', '{0812638042}', 'chien.kieu', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (18, 11, 1, false, '22 Nguyen Viet Xuan', '1997-11-13', 'chien.kieu@alpaca.vn', 'Kieu Tan Chien', true, '264520061', '$2a$10$5UKXIfB.9dSWAqr3qGi66OB4KcTkgA1BWclMrpSWNq34Zdcf6JQC.', '{0812638042}', 'chien.kieu', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (24, 27, 0, true, 'Excepteur', '1999-03-30', 'email123@gmail.com.vn', 'Fullname Placehodler', true, '798546123', '$2a$10$6bC548ZWWzntooE1/aGN0OOtrZYoVDYsgAMDQTtm0NIZBensxKFv6', '{123456789}', 'user03', 3);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (18, 28, 1, false, 'Update address placeholder', '1997-03-20', 'updated_email@gmail.com', 'Updated placehodler', false, '123753159', '$2a$10$P2oj9f0kxbafIfJvgKEZrOzPq/Oo2xl3Kk55nFKv2L0ukuY9dqsyC', '{123456789}', 'user18', 2);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (1, 30, 1, true, 'Address placeholder', '2000-01-01', 'emailplaceholder@email.com', 'Admin', true, '123456789', '$2a$10$Sa5.ZqvWQEstDncDJfOdeerazovzTPe6N2pPGIctXQ3wL.AJD9Q76', '{123456789}', 'alpaca_admin', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (1, 31, 1, true, 'Address placeholder', '2000-01-01', 'emailplaceholder@email.com', 'Admin', true, '123456789', '$2a$10$Sa5.ZqvWQEstDncDJfOdeerazovzTPe6N2pPGIctXQ3wL.AJD9Q76', '{123456789}', 'alpaca_admin', 1);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (25, 33, 0, true, 'velit dolor', '1988-10-03', 'mollit sunt commodo Excepteur', 'cupidatat dolore consequat ea', true, 'minim', '$2a$10$MMr1/qnejxE5lpEUQsw/6u4n/Ahlh7uGCD4dNu6r0/J5VUP1h55Ge', '{"sed pariatur",incididunt}', 'fugiat', 2);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (30, 34, 0, false, 'velit dolor', '1988-10-03', 'mol@', 'cupidatat dolore consequat ea', true, 'minim4545', '$2a$10$BVI5LFIif/kXA9PF46qTJe84L1ptFVdwsAl4YVvYZKevqGY7ZvOKu', '{"sed pariatur",incididunt}', 'fugiat23', 2);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (18, 35, 1, false, 'velit dolor', '1988-10-03', 'chienkieu@fpt.edu.vn', 'fullnamene', true, '123456789756', '$2a$10$ueul6cEZdO3cI5axMQSq4.auiN3liQpv9pORgR05Ir9XU4betZp8e', '{123456789,785611461}', 'user1818', 3);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (24, 36, 1, false, 'Excepteur', '1999-03-30', 'email123@gmail.com.vn', 'Fullname Placehodler', true, '798546123', '$2a$10$6bC548ZWWzntooE1/aGN0OOtrZYoVDYsgAMDQTtm0NIZBensxKFv6', '{123456789}', 'user03', 3);
INSERT INTO public.users_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) VALUES (24, 37, 1, true, 'Excepteur', '1999-03-30', 'email123@gmail.com.vn', 'Fullname Placehodler', true, '798546123', '$2a$10$6bC548ZWWzntooE1/aGN0OOtrZYoVDYsgAMDQTtm0NIZBensxKFv6', '{123456789}', 'user03', 3);


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 208
-- Name: analyzed_receipts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.analyzed_receipts_id_seq', 1, false);


--
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 214
-- Name: authorities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.authorities_id_seq', 32, true);


--
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 209
-- Name: claim_requests_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.claim_requests_id_seq', 5, true);


--
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 210
-- Name: contracts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.contracts_id_seq', 3, true);


--
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 211
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.customers_id_seq', 13, true);


--
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 227
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.hibernate_sequence', 37, true);


--
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 212
-- Name: payments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.payments_id_seq', 2, true);


--
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 207
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.roles_id_seq', 4, true);


--
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 213
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.users_id_seq', 30, true);


--
-- TOC entry 2980 (class 2606 OID 16908)
-- Name: analyzed_receipts_aud analyzed_receipts_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.analyzed_receipts_aud
    ADD CONSTRAINT analyzed_receipts_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2955 (class 2606 OID 16744)
-- Name: analyzed_receipts analyzed_receipts_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.analyzed_receipts
    ADD CONSTRAINT analyzed_receipts_pkey PRIMARY KEY (id);


--
-- TOC entry 2982 (class 2606 OID 16913)
-- Name: authorities_aud authorities_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.authorities_aud
    ADD CONSTRAINT authorities_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2976 (class 2606 OID 16871)
-- Name: authorities authorities_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT authorities_pk PRIMARY KEY (id);


--
-- TOC entry 2984 (class 2606 OID 16921)
-- Name: claim_requests_aud claim_requests_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.claim_requests_aud
    ADD CONSTRAINT claim_requests_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2957 (class 2606 OID 16752)
-- Name: claim_requests claim_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.claim_requests
    ADD CONSTRAINT claim_requests_pkey PRIMARY KEY (id);


--
-- TOC entry 2986 (class 2606 OID 16929)
-- Name: contracts_aud contracts_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts_aud
    ADD CONSTRAINT contracts_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2960 (class 2606 OID 16760)
-- Name: contracts contracts_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT contracts_pkey PRIMARY KEY (id);


--
-- TOC entry 2988 (class 2606 OID 16937)
-- Name: customers_aud customers_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.customers_aud
    ADD CONSTRAINT customers_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2963 (class 2606 OID 16768)
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- TOC entry 2990 (class 2606 OID 16942)
-- Name: payments_aud payments_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.payments_aud
    ADD CONSTRAINT payments_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2965 (class 2606 OID 16773)
-- Name: payments payments_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (id);


--
-- TOC entry 2992 (class 2606 OID 16947)
-- Name: revinfo revinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.revinfo
    ADD CONSTRAINT revinfo_pkey PRIMARY KEY (rev);


--
-- TOC entry 2994 (class 2606 OID 16952)
-- Name: roles_aud roles_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles_aud
    ADD CONSTRAINT roles_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2996 (class 2606 OID 16957)
-- Name: roles_authorities_aud roles_authorities_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles_authorities_aud
    ADD CONSTRAINT roles_authorities_aud_pkey PRIMARY KEY (rev, role_id, authority_id);


--
-- TOC entry 2978 (class 2606 OID 16876)
-- Name: roles_authorities roles_authorities_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles_authorities
    ADD CONSTRAINT roles_authorities_pk PRIMARY KEY (role_id, authority_id);


--
-- TOC entry 2968 (class 2606 OID 16778)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2998 (class 2606 OID 16965)
-- Name: users_aud users_aud_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users_aud
    ADD CONSTRAINT users_aud_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2971 (class 2606 OID 16786)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2973 (class 1259 OID 16869)
-- Name: authorities_id_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX authorities_id_uindex ON public.authorities USING btree (id);


--
-- TOC entry 2974 (class 1259 OID 17017)
-- Name: authorities_permission_name_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX authorities_permission_name_uindex ON public.authorities USING btree (permission_name);


--
-- TOC entry 2958 (class 1259 OID 17026)
-- Name: contracts_contract_code_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX contracts_contract_code_uindex ON public.contracts USING btree (contract_code);


--
-- TOC entry 2961 (class 1259 OID 17016)
-- Name: customers_id_card_number_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX customers_id_card_number_uindex ON public.customers USING btree (id_card_number);


--
-- TOC entry 2966 (class 1259 OID 16887)
-- Name: roles_name_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX roles_name_uindex ON public.roles USING btree (name);


--
-- TOC entry 2969 (class 1259 OID 17013)
-- Name: users_id_card_number_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_id_card_number_uindex ON public.users USING btree (id_card_number);


--
-- TOC entry 2972 (class 1259 OID 17014)
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);


--
-- TOC entry 3016 (class 2606 OID 17003)
-- Name: roles_authorities_aud fk3e3cm3mdi8enyv01co2w57c7n; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles_authorities_aud
    ADD CONSTRAINT fk3e3cm3mdi8enyv01co2w57c7n FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3001 (class 2606 OID 16811)
-- Name: claim_requests fk6dqaskyrunsm6qv4l2gbfaoyt; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.claim_requests
    ADD CONSTRAINT fk6dqaskyrunsm6qv4l2gbfaoyt FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- TOC entry 3013 (class 2606 OID 16988)
-- Name: customers_aud fk6yi01p88qesad1xo7fjc7qrx2; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.customers_aud
    ADD CONSTRAINT fk6yi01p88qesad1xo7fjc7qrx2 FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3011 (class 2606 OID 16978)
-- Name: claim_requests_aud fk73xec8q3ks6mnfx1mgtduxcbs; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.claim_requests_aud
    ADD CONSTRAINT fk73xec8q3ks6mnfx1mgtduxcbs FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3004 (class 2606 OID 16826)
-- Name: payments fk81ibanmxf082anggqy067nn79; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT fk81ibanmxf082anggqy067nn79 FOREIGN KEY (accountant_id) REFERENCES public.users(id);


--
-- TOC entry 3012 (class 2606 OID 16983)
-- Name: contracts_aud fk8cj1xwh653ixniwuy7xb8u6jv; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts_aud
    ADD CONSTRAINT fk8cj1xwh653ixniwuy7xb8u6jv FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3002 (class 2606 OID 16816)
-- Name: claim_requests fkaicm0cf4ccm5o5l2x50f20k2y; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.claim_requests
    ADD CONSTRAINT fkaicm0cf4ccm5o5l2x50f20k2y FOREIGN KEY (employee_id) REFERENCES public.users(id);


--
-- TOC entry 3017 (class 2606 OID 17008)
-- Name: users_aud fkc4vk4tui2la36415jpgm9leoq; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users_aud
    ADD CONSTRAINT fkc4vk4tui2la36415jpgm9leoq FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3000 (class 2606 OID 16806)
-- Name: analyzed_receipts fkebnak7oqi9cu12cmg1r006ptc; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.analyzed_receipts
    ADD CONSTRAINT fkebnak7oqi9cu12cmg1r006ptc FOREIGN KEY (request_id) REFERENCES public.claim_requests(id);


--
-- TOC entry 3010 (class 2606 OID 16973)
-- Name: authorities_aud fkewrx98i3dwo3cgtjehkqh348f; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.authorities_aud
    ADD CONSTRAINT fkewrx98i3dwo3cgtjehkqh348f FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3014 (class 2606 OID 16993)
-- Name: payments_aud fkgc1mbsc63x8g8v1d3etr9nyjc; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.payments_aud
    ADD CONSTRAINT fkgc1mbsc63x8g8v1d3etr9nyjc FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3003 (class 2606 OID 16821)
-- Name: contracts fkgcu7bfqv1j7nltm5uhk91kxcy; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT fkgcu7bfqv1j7nltm5uhk91kxcy FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- TOC entry 2999 (class 2606 OID 16801)
-- Name: analyzed_receipts fkhk70k9gms66pkuyny7wcvhvix; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.analyzed_receipts
    ADD CONSTRAINT fkhk70k9gms66pkuyny7wcvhvix FOREIGN KEY (analyzer_id) REFERENCES public.users(id);


--
-- TOC entry 3005 (class 2606 OID 16831)
-- Name: payments fki8cywuspxq1wxedv8d7v76k18; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT fki8cywuspxq1wxedv8d7v76k18 FOREIGN KEY (request_id) REFERENCES public.claim_requests(id);


--
-- TOC entry 3009 (class 2606 OID 16968)
-- Name: analyzed_receipts_aud fkiyqhfghv5m435x5rghwn0aydp; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.analyzed_receipts_aud
    ADD CONSTRAINT fkiyqhfghv5m435x5rghwn0aydp FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3006 (class 2606 OID 16836)
-- Name: users fkp56c1712k691lhsyewcssf40f; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkp56c1712k691lhsyewcssf40f FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3015 (class 2606 OID 16998)
-- Name: roles_aud fkt0mnl3rej2p0h9gxnbalf2kdd; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles_aud
    ADD CONSTRAINT fkt0mnl3rej2p0h9gxnbalf2kdd FOREIGN KEY (rev) REFERENCES public.revinfo(rev);


--
-- TOC entry 3008 (class 2606 OID 16882)
-- Name: roles_authorities roles_authorities_authorities_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles_authorities
    ADD CONSTRAINT roles_authorities_authorities_id_fk FOREIGN KEY (authority_id) REFERENCES public.authorities(id);


--
-- TOC entry 3007 (class 2606 OID 16877)
-- Name: roles_authorities roles_authorities_roles_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles_authorities
    ADD CONSTRAINT roles_authorities_roles_id_fk FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3182 (class 0 OID 0)
-- Dependencies: 3181
-- Name: DATABASE alpaca_pj_01; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE alpaca_pj_01 TO alpaca;


-- Completed on 2021-10-09 20:50:21

--
-- PostgreSQL database dump complete
--

