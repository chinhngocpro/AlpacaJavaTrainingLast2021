--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

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

DROP DATABASE IF EXISTS alpaca_pj_01;
--
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

--
-- Name: claim_request_management; Type: SCHEMA; Schema: -; Owner: alpaca
--

CREATE SCHEMA claim_request_management;


ALTER SCHEMA claim_request_management OWNER TO alpaca;

--
-- Name: customer_management; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA customer_management;


ALTER SCHEMA customer_management OWNER TO postgres;

--
-- Name: SCHEMA customer_management; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA customer_management IS 'standard public schema';


--
-- Name: oauth2; Type: SCHEMA; Schema: -; Owner: alpaca
--

CREATE SCHEMA oauth2;


ALTER SCHEMA oauth2 OWNER TO alpaca;

--
-- Name: user_management; Type: SCHEMA; Schema: -; Owner: alpaca
--

CREATE SCHEMA user_management;


ALTER SCHEMA user_management OWNER TO alpaca;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: analyzed_receipts; Type: TABLE; Schema: claim_request_management; Owner: alpaca
--

CREATE TABLE claim_request_management.analyzed_receipts (
    id integer NOT NULL,
    accident_id integer,
    amount double precision,
    hospital_id integer,
    is_valid boolean,
    title character varying(255),
    request_id integer
);


ALTER TABLE claim_request_management.analyzed_receipts OWNER TO alpaca;

--
-- Name: analyzed_receipts_id_seq; Type: SEQUENCE; Schema: claim_request_management; Owner: alpaca
--

CREATE SEQUENCE claim_request_management.analyzed_receipts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE claim_request_management.analyzed_receipts_id_seq OWNER TO alpaca;

--
-- Name: analyzed_receipts_id_seq; Type: SEQUENCE OWNED BY; Schema: claim_request_management; Owner: alpaca
--

ALTER SEQUENCE claim_request_management.analyzed_receipts_id_seq OWNED BY claim_request_management.analyzed_receipts.id;


--
-- Name: claim_requests; Type: TABLE; Schema: claim_request_management; Owner: alpaca
--

CREATE TABLE claim_request_management.claim_requests (
    id integer NOT NULL,
    description character varying(255),
    title character varying(255),
    receipt_photos text,
    status character varying(255),
    customer_id integer
);


ALTER TABLE claim_request_management.claim_requests OWNER TO alpaca;

--
-- Name: claim_requests_id_seq; Type: SEQUENCE; Schema: claim_request_management; Owner: alpaca
--

CREATE SEQUENCE claim_request_management.claim_requests_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE claim_request_management.claim_requests_id_seq OWNER TO alpaca;

--
-- Name: claim_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: claim_request_management; Owner: alpaca
--

ALTER SEQUENCE claim_request_management.claim_requests_id_seq OWNED BY claim_request_management.claim_requests.id;


--
-- Name: payments; Type: TABLE; Schema: claim_request_management; Owner: alpaca
--

CREATE TABLE claim_request_management.payments (
    id integer NOT NULL,
    amount double precision,
    payment_date timestamp without time zone,
    accountant_id integer,
    request_id integer
);


ALTER TABLE claim_request_management.payments OWNER TO alpaca;

--
-- Name: payments_id_seq; Type: SEQUENCE; Schema: claim_request_management; Owner: alpaca
--

CREATE SEQUENCE claim_request_management.payments_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE claim_request_management.payments_id_seq OWNER TO alpaca;

--
-- Name: payments_id_seq; Type: SEQUENCE OWNED BY; Schema: claim_request_management; Owner: alpaca
--

ALTER SEQUENCE claim_request_management.payments_id_seq OWNED BY claim_request_management.payments.id;


--
-- Name: contracts; Type: TABLE; Schema: customer_management; Owner: alpaca
--

CREATE TABLE customer_management.contracts (
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


ALTER TABLE customer_management.contracts OWNER TO alpaca;

--
-- Name: contracts_aud; Type: TABLE; Schema: customer_management; Owner: alpaca
--

CREATE TABLE customer_management.contracts_aud (
    id integer NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    acceptable_accident_ids integer[],
    acceptable_hospital_ids integer[],
    active boolean,
    contract_code character varying(255),
    customer_id integer,
    maximum_amount double precision,
    remaining_amount double precision,
    valid_from timestamp without time zone,
    valid_to timestamp without time zone
);


ALTER TABLE customer_management.contracts_aud OWNER TO alpaca;

--
-- Name: contracts_id_seq; Type: SEQUENCE; Schema: customer_management; Owner: alpaca
--

CREATE SEQUENCE customer_management.contracts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_management.contracts_id_seq OWNER TO alpaca;

--
-- Name: contracts_id_seq; Type: SEQUENCE OWNED BY; Schema: customer_management; Owner: alpaca
--

ALTER SEQUENCE customer_management.contracts_id_seq OWNED BY customer_management.contracts.id;


--
-- Name: customers; Type: TABLE; Schema: customer_management; Owner: alpaca
--

CREATE TABLE customer_management.customers (
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


ALTER TABLE customer_management.customers OWNER TO alpaca;

--
-- Name: customers_aud; Type: TABLE; Schema: customer_management; Owner: alpaca
--

CREATE TABLE customer_management.customers_aud (
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


ALTER TABLE customer_management.customers_aud OWNER TO alpaca;

--
-- Name: customers_id_seq; Type: SEQUENCE; Schema: customer_management; Owner: alpaca
--

CREATE SEQUENCE customer_management.customers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_management.customers_id_seq OWNER TO alpaca;

--
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: customer_management; Owner: alpaca
--

ALTER SEQUENCE customer_management.customers_id_seq OWNED BY customer_management.customers.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: customer_management; Owner: alpaca
--

CREATE SEQUENCE customer_management.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_management.hibernate_sequence OWNER TO alpaca;

--
-- Name: revinfo; Type: TABLE; Schema: customer_management; Owner: alpaca
--

CREATE TABLE customer_management.revinfo (
    rev integer NOT NULL,
    revtstmp bigint
);


ALTER TABLE customer_management.revinfo OWNER TO alpaca;

--
-- Name: oauth_client_details; Type: TABLE; Schema: oauth2; Owner: alpaca
--

CREATE TABLE oauth2.oauth_client_details (
    client_id character varying(255) NOT NULL,
    client_secret character varying(255) NOT NULL,
    resource_ids character varying(255),
    scope character varying(255),
    authorized_grant_types character varying(255),
    web_server_redirect_uri character varying(255),
    authorities character varying(255),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information jsonb,
    autoapprove character varying(255)
);


ALTER TABLE oauth2.oauth_client_details OWNER TO alpaca;

--
-- Name: authorities; Type: TABLE; Schema: user_management; Owner: alpaca
--

CREATE TABLE user_management.authorities (
    id integer NOT NULL,
    permission_name character varying(50)
);


ALTER TABLE user_management.authorities OWNER TO alpaca;

--
-- Name: authorities_id_seq; Type: SEQUENCE; Schema: user_management; Owner: alpaca
--

CREATE SEQUENCE user_management.authorities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_management.authorities_id_seq OWNER TO alpaca;

--
-- Name: authorities_id_seq; Type: SEQUENCE OWNED BY; Schema: user_management; Owner: alpaca
--

ALTER SEQUENCE user_management.authorities_id_seq OWNED BY user_management.authorities.id;


--
-- Name: roles; Type: TABLE; Schema: user_management; Owner: alpaca
--

CREATE TABLE user_management.roles (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE user_management.roles OWNER TO alpaca;

--
-- Name: roles_authorities; Type: TABLE; Schema: user_management; Owner: alpaca
--

CREATE TABLE user_management.roles_authorities (
    role_id integer NOT NULL,
    authority_id integer NOT NULL
);


ALTER TABLE user_management.roles_authorities OWNER TO alpaca;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: user_management; Owner: alpaca
--

CREATE SEQUENCE user_management.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_management.roles_id_seq OWNER TO alpaca;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: user_management; Owner: alpaca
--

ALTER SEQUENCE user_management.roles_id_seq OWNED BY user_management.roles.id;


--
-- Name: users; Type: TABLE; Schema: user_management; Owner: alpaca
--

CREATE TABLE user_management.users (
    id integer NOT NULL,
    active boolean NOT NULL,
    address character varying(255) NOT NULL,
    date_of_birth date NOT NULL,
    email character varying(255) NOT NULL,
    full_name character varying(255) NOT NULL,
    gender boolean NOT NULL,
    id_card_number character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone_numbers text[] NOT NULL,
    username character varying(255) NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE user_management.users OWNER TO alpaca;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: user_management; Owner: alpaca
--

CREATE SEQUENCE user_management.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_management.users_id_seq OWNER TO alpaca;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: user_management; Owner: alpaca
--

ALTER SEQUENCE user_management.users_id_seq OWNED BY user_management.users.id;


--
-- Name: analyzed_receipts id; Type: DEFAULT; Schema: claim_request_management; Owner: alpaca
--

ALTER TABLE ONLY claim_request_management.analyzed_receipts ALTER COLUMN id SET DEFAULT nextval('claim_request_management.analyzed_receipts_id_seq'::regclass);


--
-- Name: claim_requests id; Type: DEFAULT; Schema: claim_request_management; Owner: alpaca
--

ALTER TABLE ONLY claim_request_management.claim_requests ALTER COLUMN id SET DEFAULT nextval('claim_request_management.claim_requests_id_seq'::regclass);


--
-- Name: payments id; Type: DEFAULT; Schema: claim_request_management; Owner: alpaca
--

ALTER TABLE ONLY claim_request_management.payments ALTER COLUMN id SET DEFAULT nextval('claim_request_management.payments_id_seq'::regclass);


--
-- Name: contracts id; Type: DEFAULT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.contracts ALTER COLUMN id SET DEFAULT nextval('customer_management.contracts_id_seq'::regclass);


--
-- Name: customers id; Type: DEFAULT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.customers ALTER COLUMN id SET DEFAULT nextval('customer_management.customers_id_seq'::regclass);


--
-- Name: authorities id; Type: DEFAULT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.authorities ALTER COLUMN id SET DEFAULT nextval('user_management.authorities_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.roles ALTER COLUMN id SET DEFAULT nextval('user_management.roles_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.users ALTER COLUMN id SET DEFAULT nextval('user_management.users_id_seq'::regclass);


--
-- Data for Name: analyzed_receipts; Type: TABLE DATA; Schema: claim_request_management; Owner: alpaca
--

COPY claim_request_management.analyzed_receipts (id, accident_id, amount, hospital_id, is_valid, title, request_id) FROM stdin;
\.


--
-- Data for Name: claim_requests; Type: TABLE DATA; Schema: claim_request_management; Owner: alpaca
--

COPY claim_request_management.claim_requests (id, description, title, receipt_photos, status, customer_id) FROM stdin;
4	Description Placeholder	Title Placeholder	{1eb60440-15fd-4bb3-84ec-a13f6f68498f.jpg,e1e55ce2-bc63-4b07-8cd8-71be3cae8970.jpg,85b3340e-8bd3-4b16-a47a-fa1b15288911.jpg,7baae55c-e53e-4cb6-adf5-b21e0af6aeb9.jpg}	PENDING	3
5	đâsdas,dsadasdasd	ádasdasda,Title placeholder	{7de79702-7424-40f4-9057-25717f19767c.jpg,8b6133a8-c239-48ca-b54b-e5d065c6d118.jpg,6273c5dd-3089-4088-9e62-19448bac4638.jpg,589aad60-989a-4556-b36e-ea14701a9fc8.jpg,33667edd-79dc-4226-9604-20ad75cfb26a.jfif}	PENDING	3
1	Description Placeholder	Title Placeholder		PENDING	1
2	Description Placeholder	Title Placeholder		PENDING	2
3	Description Placeholder	Title Placeholder	{c768b7d0-ca65-4d20-af95-48337a84cfbe.jpg,a114a8be-6c50-4bf9-84a4-7cbc634f64a8.jpg,f3262d0e-5be0-4fb6-911d-afd07054b72b.jpg}	PENDING	3
30	dasdadasda	12313212	{8f68ba8a-808b-4654-8689-539112288161.jpg}	PENDING	3
31	dasdadasda	12313212	{d041d7c5-3a27-41ea-8b9f-8602a57efedd.jpg}	PENDING	3
32	dasdadasda	12313212	{769f9ec3-fff3-44d6-a81f-91eaeb416c55.jpg}	PENDING	3
33	dasdadasda	12313212	{ba70953f-140f-4cf7-918b-0794b57867df.jpg}	PENDING	3
34	dasdadasda	12313212	{0cd14b28-acb4-4e21-baa8-435404df3cc3.jpg}	PENDING	3
35	dasdadasda	12313212	{88c8d968-e695-4631-aea2-403ee562976c.jpg}	PENDING	3
36	dasdadasda	12313212	{c1508c80-d57e-435b-b133-3b6709364d33.jpg}	PENDING	3
37	dasdadasda	12313212	{3d82b9fd-4ee1-4530-97f9-5d79a80763ff.jpg}	PENDING	3
38	dasdadasda	12313212	{46dd5173-98d0-4ff0-89da-29173ce9f401.jpg}	PENDING	3
39	dasdadasda	12313212	{5ec884bc-912c-428e-8e80-55ef484b160b.jpg}	PENDING	3
40	dasdadasda	12313212	{849e016d-68df-4b3a-ad5d-81af9223d710.jpg}	PENDING	3
41	dasdadasda	12313212	{3486ecb5-3715-4045-91bc-a525b1e5e0e2.jpg}	PENDING	3
42	dasdadasda	12313212	{97be5b12-5845-40fb-aa12-9bf0edb5d424.jpg}	PENDING	3
43	dasdadasda	12313212	{2af7bd57-a206-4110-8bdb-30c60acc1ce6.jpg}	PENDING	3
\.


--
-- Data for Name: payments; Type: TABLE DATA; Schema: claim_request_management; Owner: alpaca
--

COPY claim_request_management.payments (id, amount, payment_date, accountant_id, request_id) FROM stdin;
1	500000	2021-09-17 02:10:45.717	1	4
2	500000	2021-09-17 02:10:45.717	1	4
\.


--
-- Data for Name: contracts; Type: TABLE DATA; Schema: customer_management; Owner: alpaca
--

COPY customer_management.contracts (id, acceptable_accident_ids, acceptable_hospital_ids, active, contract_code, maximum_amount, remaining_amount, valid_from, valid_to, customer_id) FROM stdin;
3	{1,2,3,4}	{1,2,3}	t	HDV123456789	5000000	1000000	1992-12-15 16:35:47.483	2030-10-30 07:49:34.471	2
2	{3,4}	{1,2}	t	HDK789456123	5000000	200000	1991-12-08 18:31:21.316	2015-08-14 06:26:27.491	3
\.


--
-- Data for Name: contracts_aud; Type: TABLE DATA; Schema: customer_management; Owner: alpaca
--

COPY customer_management.contracts_aud (id, rev, revtype, acceptable_accident_ids, acceptable_hospital_ids, active, contract_code, customer_id, maximum_amount, remaining_amount, valid_from, valid_to) FROM stdin;
\.


--
-- Data for Name: customers; Type: TABLE DATA; Schema: customer_management; Owner: alpaca
--

COPY customer_management.customers (id, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) FROM stdin;
3	t	Address placeholder	1978-12-11	email@gmail.com	Full name placeholder	t	123456789	Student	{123456789,123456789}
10	t	Address placeholder	1978-12-11	email@gmail.com	Full name placeholder	t	123456783	Student	{123456789,123456789}
1	f	Address placeholder	1978-12-11	email@gmail.com	Full name placeholder	t	123456786	Student	{123456789,123456789}
4	t	Address placeholder	1978-12-11	email@gmail.com	Full name placeholder	t	123456788	Student	{123456789,123456789}
6	t	Address placeholder	1978-12-11	email@gmail.com	Full name placeholder	t	123456785	Student	{123456789,123456789}
2	t	Updated address placeholder	1978-12-11	updated_email@gmail.com	Updated full name placeholder	f	123456787	Student	{123456789,123456789}
8	t	Address placeholder	1978-12-11	email@gmail.com	Full name placeholder	t	123456784	Student	{123456789,123456789}
\.


--
-- Data for Name: customers_aud; Type: TABLE DATA; Schema: customer_management; Owner: alpaca
--

COPY customer_management.customers_aud (id, rev, revtype, active, address, date_of_birth, email, full_name, gender, id_card_number, occupation, phone_numbers) FROM stdin;
\.


--
-- Data for Name: revinfo; Type: TABLE DATA; Schema: customer_management; Owner: alpaca
--

COPY customer_management.revinfo (rev, revtstmp) FROM stdin;
39	1634265372543
40	1634265521542
41	1634265843173
42	1634265935928
43	1634266040266
44	1634266271722
45	1634266479173
46	1634266672723
47	1634266746792
48	1634266748664
49	1634266840681
50	1634266845147
51	1634266855195
52	1634269976012
53	1634269977570
54	1634269997589
55	1634270009383
56	1634270019816
57	1634270030922
58	1634270069663
59	1634270096808
60	1634270103804
61	1634270107880
62	1634294210181
63	1634294211829
64	1634294212858
65	1634294213647
66	1634294214518
67	1634294215283
68	1634294216019
69	1634294216785
70	1634294217398
71	1634294218103
72	1634294218845
73	1634294219477
74	1634294220070
75	1634294220642
\.


--
-- Data for Name: oauth_client_details; Type: TABLE DATA; Schema: oauth2; Owner: alpaca
--

COPY oauth2.oauth_client_details (client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) FROM stdin;
\.


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: user_management; Owner: alpaca
--

COPY user_management.authorities (id, permission_name) FROM stdin;
1	USER_READ
2	USER_CREATE
3	USER_UPDATE
4	USER_DELETE
9	AUTHORITY_READ
10	AUTHORITY_CREATE
11	AUTHORITY_UPDATE
12	AUTHORITY_DELETE
13	CUSTOMER_READ
14	CUSTOMER_CREATE
15	CUSTOMER_UPDATE
16	CUSTOMER_DELETE
17	CLAIM_REQUEST_READ
18	CLAIM_REQUEST_CREATE
19	CLAIM_REQUEST_UPDATE
20	CLAIM_REQUEST_DELETE
21	ANALYZED_RECEIPT_READ
22	ANALYZED_RECEIPT_CREATE
23	ANALYZED_RECEIPT_UPDATE
24	ANALYZED_RECEIPT_DELETE
25	CONTRACT_READ
26	CONTRACT_CREATE
27	CONTRACT_UPDATE
28	CONTRACT_DELETE
29	PAYMENT_READ
30	PAYMENT_CREATE
31	PAYMENT_UPDATE
32	PAYMENT_DELETE
5	SYSTEM_ROLE_READ
6	SYSTEM_ROLE_CREATE
7	SYSTEM_ROLE_UPDATE
8	SYSTEM_ROLE_DELETE
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: user_management; Owner: alpaca
--

COPY user_management.roles (id, name) FROM stdin;
1	ADMIN
2	EMPLOYEE
4	ACCOUNTANT
3	ANALYZER
\.


--
-- Data for Name: roles_authorities; Type: TABLE DATA; Schema: user_management; Owner: alpaca
--

COPY user_management.roles_authorities (role_id, authority_id) FROM stdin;
1	1
1	2
1	3
1	4
1	5
1	6
1	7
1	8
1	9
1	10
1	11
1	12
1	13
1	14
1	15
1	16
1	17
1	18
1	19
1	20
1	21
1	22
1	23
1	24
1	25
1	26
1	27
1	28
1	29
1	30
1	31
1	32
2	13
2	14
2	17
2	19
2	21
2	25
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: user_management; Owner: alpaca
--

COPY user_management.users (id, active, address, date_of_birth, email, full_name, gender, id_card_number, password, phone_numbers, username, role_id) FROM stdin;
18	f	velit dolor	1988-10-03	chienkieu@fpt.edu.vn	fullnamene	t	123456789756	$2a$10$ueul6cEZdO3cI5axMQSq4.auiN3liQpv9pORgR05Ir9XU4betZp8e	{123456789,785611461}	user1818	3
24	t	Excepteur	1999-03-30	email123@gmail.com.vn	Fullname Placehodler	t	798546123	$2a$10$6bC548ZWWzntooE1/aGN0OOtrZYoVDYsgAMDQTtm0NIZBensxKFv6	{123456789}	user03	3
5	t	22 Nguyen Viet Xuan	1997-11-13	chien.kieu@alpaca.vn	Kieu Tan Chien	t	26452006112	$2a$10$ZE7M/Gy1dHiSZ1DZNVxHzO6Klkqv3eaLe2bhi7s1UfQmXU8XZZ0bi	{0812638042}	kieutanchien123	2
6	t	22 Nguyen Viet Xuan	1997-11-13	chien.kieu@alpaca.vn	Kieu Tan Chien	t	26452006113	$2a$10$ZE7M/Gy1dHiSZ1DZNVxHzO6Klkqv3eaLe2bhi7s1UfQmXU8XZZ0bi	{0812638042}	kieutanchien1234	2
25	t	velit dolor	1988-10-03	mollit sunt commodo Excepteur	cupidatat dolore consequat ea	t	123484656	$2a$10$MMr1/qnejxE5lpEUQsw/6u4n/Ahlh7uGCD4dNu6r0/J5VUP1h55Ge	{"sed pariatur",incididunt}	fugiat	2
30	f	velit dolor	1988-10-03	mol@	cupidatat dolore consequat ea	t	845652661	$2a$10$BVI5LFIif/kXA9PF46qTJe84L1ptFVdwsAl4YVvYZKevqGY7ZvOKu	{"sed pariatur",incididunt}	fugiat23	2
1	t	22 Nguyen Viet Xuan, Khu Pho 8, Phuoc Dan, Ninh Phuoc, Ninh Thuan	1997-11-13	chien.kieu@alpaca.vn	Kieu Tan Chien	t	264520061	$2a$10$4kooqYXNeCm5duhJcprJ5.geyitFwx3kBTxhOQjj7z0Zf1xBrgrMq	{0812638042,0344203330}	kieutanchien	1
\.


--
-- Name: analyzed_receipts_id_seq; Type: SEQUENCE SET; Schema: claim_request_management; Owner: alpaca
--

SELECT pg_catalog.setval('claim_request_management.analyzed_receipts_id_seq', 1, false);


--
-- Name: claim_requests_id_seq; Type: SEQUENCE SET; Schema: claim_request_management; Owner: alpaca
--

SELECT pg_catalog.setval('claim_request_management.claim_requests_id_seq', 1, false);


--
-- Name: payments_id_seq; Type: SEQUENCE SET; Schema: claim_request_management; Owner: alpaca
--

SELECT pg_catalog.setval('claim_request_management.payments_id_seq', 1, false);


--
-- Name: contracts_id_seq; Type: SEQUENCE SET; Schema: customer_management; Owner: alpaca
--

SELECT pg_catalog.setval('customer_management.contracts_id_seq', 3, true);


--
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: customer_management; Owner: alpaca
--

SELECT pg_catalog.setval('customer_management.customers_id_seq', 14, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: customer_management; Owner: alpaca
--

SELECT pg_catalog.setval('customer_management.hibernate_sequence', 75, true);


--
-- Name: authorities_id_seq; Type: SEQUENCE SET; Schema: user_management; Owner: alpaca
--

SELECT pg_catalog.setval('user_management.authorities_id_seq', 1, false);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: user_management; Owner: alpaca
--

SELECT pg_catalog.setval('user_management.roles_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: user_management; Owner: alpaca
--

SELECT pg_catalog.setval('user_management.users_id_seq', 8, true);


--
-- Name: contracts_aud contracts_aud_pkey; Type: CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.contracts_aud
    ADD CONSTRAINT contracts_aud_pkey PRIMARY KEY (id, rev);


--
-- Name: contracts contracts_pkey; Type: CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.contracts
    ADD CONSTRAINT contracts_pkey PRIMARY KEY (id);


--
-- Name: customers_aud customers_aud_pkey; Type: CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.customers_aud
    ADD CONSTRAINT customers_aud_pkey PRIMARY KEY (id, rev);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: revinfo revinfo_pkey; Type: CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.revinfo
    ADD CONSTRAINT revinfo_pkey PRIMARY KEY (rev);


--
-- Name: oauth_client_details oauth_client_details_pk; Type: CONSTRAINT; Schema: oauth2; Owner: alpaca
--

ALTER TABLE ONLY oauth2.oauth_client_details
    ADD CONSTRAINT oauth_client_details_pk PRIMARY KEY (client_id);


--
-- Name: authorities authorities_pk; Type: CONSTRAINT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.authorities
    ADD CONSTRAINT authorities_pk PRIMARY KEY (id);


--
-- Name: roles_authorities roles_authorities_pk; Type: CONSTRAINT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.roles_authorities
    ADD CONSTRAINT roles_authorities_pk PRIMARY KEY (role_id, authority_id);


--
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: contracts_contract_code_uindex; Type: INDEX; Schema: customer_management; Owner: alpaca
--

CREATE UNIQUE INDEX contracts_contract_code_uindex ON customer_management.contracts USING btree (contract_code);


--
-- Name: customers_id_card_number_uindex; Type: INDEX; Schema: customer_management; Owner: alpaca
--

CREATE UNIQUE INDEX customers_id_card_number_uindex ON customer_management.customers USING btree (id_card_number);


--
-- Name: authorities_id_uindex; Type: INDEX; Schema: user_management; Owner: alpaca
--

CREATE UNIQUE INDEX authorities_id_uindex ON user_management.authorities USING btree (id);


--
-- Name: roles_id_uindex; Type: INDEX; Schema: user_management; Owner: alpaca
--

CREATE UNIQUE INDEX roles_id_uindex ON user_management.roles USING btree (id);


--
-- Name: users_id_card_number_uindex; Type: INDEX; Schema: user_management; Owner: alpaca
--

CREATE UNIQUE INDEX users_id_card_number_uindex ON user_management.users USING btree (id_card_number);


--
-- Name: users_id_uindex; Type: INDEX; Schema: user_management; Owner: alpaca
--

CREATE UNIQUE INDEX users_id_uindex ON user_management.users USING btree (id);


--
-- Name: users_username_uindex; Type: INDEX; Schema: user_management; Owner: alpaca
--

CREATE UNIQUE INDEX users_username_uindex ON user_management.users USING btree (username);


--
-- Name: customers_aud fk6yi01p88qesad1xo7fjc7qrx2; Type: FK CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.customers_aud
    ADD CONSTRAINT fk6yi01p88qesad1xo7fjc7qrx2 FOREIGN KEY (rev) REFERENCES customer_management.revinfo(rev);


--
-- Name: contracts_aud fk8cj1xwh653ixniwuy7xb8u6jv; Type: FK CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.contracts_aud
    ADD CONSTRAINT fk8cj1xwh653ixniwuy7xb8u6jv FOREIGN KEY (rev) REFERENCES customer_management.revinfo(rev);


--
-- Name: contracts fkgcu7bfqv1j7nltm5uhk91kxcy; Type: FK CONSTRAINT; Schema: customer_management; Owner: alpaca
--

ALTER TABLE ONLY customer_management.contracts
    ADD CONSTRAINT fkgcu7bfqv1j7nltm5uhk91kxcy FOREIGN KEY (customer_id) REFERENCES customer_management.customers(id);


--
-- Name: roles_authorities roles_authorities_authorities_id_fk; Type: FK CONSTRAINT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.roles_authorities
    ADD CONSTRAINT roles_authorities_authorities_id_fk FOREIGN KEY (authority_id) REFERENCES user_management.authorities(id);


--
-- Name: roles_authorities roles_authorities_roles_id_fk; Type: FK CONSTRAINT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.roles_authorities
    ADD CONSTRAINT roles_authorities_roles_id_fk FOREIGN KEY (role_id) REFERENCES user_management.roles(id);


--
-- Name: users users_roles_id_fk; Type: FK CONSTRAINT; Schema: user_management; Owner: alpaca
--

ALTER TABLE ONLY user_management.users
    ADD CONSTRAINT users_roles_id_fk FOREIGN KEY (role_id) REFERENCES user_management.roles(id);


--
-- Name: DATABASE alpaca_pj_01; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE alpaca_pj_01 TO alpaca;


--
-- PostgreSQL database dump complete
--

