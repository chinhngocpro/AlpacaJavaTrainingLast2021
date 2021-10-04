--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2021-10-04 11:35:56

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
-- TOC entry 3075 (class 1262 OID 16394)
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
-- TOC entry 202 (class 1259 OID 16451)
-- Name: contracts; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.contracts (
    id integer NOT NULL,
    contract_code character(12) NOT NULL,
    customer_id integer NOT NULL,
    valid_from timestamp without time zone NOT NULL,
    valid_to timestamp without time zone NOT NULL,
    maximum_amount money NOT NULL,
    remaining_amount money NOT NULL,
    acceptable_hospital_ids integer[] NOT NULL,
    acceptable_accident_ids integer[] NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.contracts OWNER TO alpaca;

--
-- TOC entry 203 (class 1259 OID 16454)
-- Name: Contracts_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public."Contracts_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Contracts_id_seq" OWNER TO alpaca;

--
-- TOC entry 3077 (class 0 OID 0)
-- Dependencies: 203
-- Name: Contracts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public."Contracts_id_seq" OWNED BY public.contracts.id;


--
-- TOC entry 211 (class 1259 OID 16656)
-- Name: payments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payments (
    id integer NOT NULL,
    amount money NOT NULL,
    accountant_id integer NOT NULL,
    request_id integer NOT NULL,
    payment_date timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.payments OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16654)
-- Name: Payment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Payment_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Payment_id_seq" OWNER TO postgres;

--
-- TOC entry 3078 (class 0 OID 0)
-- Dependencies: 210
-- Name: Payment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Payment_id_seq" OWNED BY public.payments.id;


--
-- TOC entry 205 (class 1259 OID 16511)
-- Name: roles; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.roles OWNER TO alpaca;

--
-- TOC entry 204 (class 1259 OID 16509)
-- Name: Roles_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public."Roles_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Roles_id_seq" OWNER TO alpaca;

--
-- TOC entry 3079 (class 0 OID 0)
-- Dependencies: 204
-- Name: Roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public."Roles_id_seq" OWNED BY public.roles.id;


--
-- TOC entry 206 (class 1259 OID 16519)
-- Name: users; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    full_name character varying(100) NOT NULL,
    gender boolean NOT NULL,
    id_card_number character varying(12) NOT NULL,
    phone_numbers text[] NOT NULL,
    email character varying(100) NOT NULL,
    date_of_birth date NOT NULL,
    address text NOT NULL,
    role_id integer NOT NULL,
    active boolean DEFAULT false NOT NULL
);


ALTER TABLE public.users OWNER TO alpaca;

--
-- TOC entry 207 (class 1259 OID 16522)
-- Name: Users_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public."Users_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Users_id_seq" OWNER TO alpaca;

--
-- TOC entry 3080 (class 0 OID 0)
-- Dependencies: 207
-- Name: Users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public."Users_id_seq" OWNED BY public.users.id;


--
-- TOC entry 209 (class 1259 OID 16641)
-- Name: analyzed_receipts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.analyzed_receipts (
    id integer NOT NULL,
    is_valid boolean NOT NULL,
    hospital_id integer NOT NULL,
    accident_id integer NOT NULL,
    name text NOT NULL,
    amount money NOT NULL,
    analyzer_id integer NOT NULL,
    request_id integer NOT NULL
);


ALTER TABLE public.analyzed_receipts OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16399)
-- Name: customers; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public.customers (
    id integer NOT NULL,
    full_name character varying(100) NOT NULL,
    gender boolean NOT NULL,
    id_card_number character varying(12) NOT NULL,
    phone_numbers text[] NOT NULL,
    email character varying(100) NOT NULL,
    date_of_birth date NOT NULL,
    address text NOT NULL,
    occupation character varying(50) NOT NULL,
    active boolean DEFAULT true NOT NULL
);


ALTER TABLE public.customers OWNER TO alpaca;

--
-- TOC entry 200 (class 1259 OID 16397)
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: alpaca
--

CREATE SEQUENCE public.customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_id_seq OWNER TO alpaca;

--
-- TOC entry 3081 (class 0 OID 0)
-- Dependencies: 200
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public.customers.id;


--
-- TOC entry 208 (class 1259 OID 16633)
-- Name: requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.requests (
    id integer NOT NULL,
    customer_id integer NOT NULL,
    name character varying(100) NOT NULL,
    description text NOT NULL,
    receipt_photos text[] NOT NULL,
    status character varying(20) DEFAULT 'PENDING'::character varying NOT NULL,
    close boolean DEFAULT false NOT NULL,
    employee_id integer
);


ALTER TABLE public.requests OWNER TO postgres;

--
-- TOC entry 2889 (class 2604 OID 16456)
-- Name: contracts id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts ALTER COLUMN id SET DEFAULT nextval('public."Contracts_id_seq"'::regclass);


--
-- TOC entry 2887 (class 2604 OID 16402)
-- Name: customers id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- TOC entry 2896 (class 2604 OID 16659)
-- Name: payments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments ALTER COLUMN id SET DEFAULT nextval('public."Payment_id_seq"'::regclass);


--
-- TOC entry 2891 (class 2604 OID 16514)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public."Roles_id_seq"'::regclass);


--
-- TOC entry 2892 (class 2604 OID 16524)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public."Users_id_seq"'::regclass);


--
-- TOC entry 3067 (class 0 OID 16641)
-- Dependencies: 209
-- Data for Name: analyzed_receipts; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3060 (class 0 OID 16451)
-- Dependencies: 202
-- Data for Name: contracts; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3059 (class 0 OID 16399)
-- Dependencies: 201
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3069 (class 0 OID 16656)
-- Dependencies: 211
-- Data for Name: payments; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3066 (class 0 OID 16633)
-- Dependencies: 208
-- Data for Name: requests; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3063 (class 0 OID 16511)
-- Dependencies: 205
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3064 (class 0 OID 16519)
-- Dependencies: 206
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3082 (class 0 OID 0)
-- Dependencies: 203
-- Name: Contracts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public."Contracts_id_seq"', 1, false);


--
-- TOC entry 3083 (class 0 OID 0)
-- Dependencies: 210
-- Name: Payment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Payment_id_seq"', 1, false);


--
-- TOC entry 3084 (class 0 OID 0)
-- Dependencies: 204
-- Name: Roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public."Roles_id_seq"', 1, false);


--
-- TOC entry 3085 (class 0 OID 0)
-- Dependencies: 207
-- Name: Users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public."Users_id_seq"', 1, false);


--
-- TOC entry 3086 (class 0 OID 0)
-- Dependencies: 200
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.customer_id_seq', 1, false);


--
-- TOC entry 2916 (class 2606 OID 16648)
-- Name: analyzed_receipts AnalyzedReceipts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyzed_receipts
    ADD CONSTRAINT "AnalyzedReceipts_pkey" PRIMARY KEY (id);


--
-- TOC entry 2918 (class 2606 OID 16661)
-- Name: payments Payment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT "Payment_pkey" PRIMARY KEY (id);


--
-- TOC entry 2914 (class 2606 OID 16640)
-- Name: requests Requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT "Requests_pkey" PRIMARY KEY (id);


--
-- TOC entry 2904 (class 2606 OID 16466)
-- Name: contracts contracts_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT contracts_pk PRIMARY KEY (id);


--
-- TOC entry 2899 (class 2606 OID 16407)
-- Name: customers customer_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customer_pk PRIMARY KEY (id);


--
-- TOC entry 2907 (class 2606 OID 16517)
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- TOC entry 2911 (class 2606 OID 16535)
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 2901 (class 1259 OID 16463)
-- Name: contracts_contract_code_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX contracts_contract_code_uindex ON public.contracts USING btree (contract_code);


--
-- TOC entry 2902 (class 1259 OID 16464)
-- Name: contracts_id_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX contracts_id_uindex ON public.contracts USING btree (id);


--
-- TOC entry 2900 (class 1259 OID 16536)
-- Name: customers_id_card_number_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX customers_id_card_number_uindex ON public.customers USING btree (id_card_number);


--
-- TOC entry 2905 (class 1259 OID 16515)
-- Name: roles_id_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX roles_id_uindex ON public.roles USING btree (id);


--
-- TOC entry 2908 (class 1259 OID 16531)
-- Name: users_id_card_number_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_id_card_number_uindex ON public.users USING btree (id_card_number);


--
-- TOC entry 2909 (class 1259 OID 16532)
-- Name: users_id_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);


--
-- TOC entry 2912 (class 1259 OID 16533)
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);


--
-- TOC entry 2924 (class 2606 OID 16677)
-- Name: analyzed_receipts AnalyzedReceipts_analyzer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyzed_receipts
    ADD CONSTRAINT "AnalyzedReceipts_analyzer_id_fkey" FOREIGN KEY (analyzer_id) REFERENCES public.users(id) NOT VALID;


--
-- TOC entry 2925 (class 2606 OID 16682)
-- Name: analyzed_receipts AnalyzedReceipts_request_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.analyzed_receipts
    ADD CONSTRAINT "AnalyzedReceipts_request_id_fkey" FOREIGN KEY (request_id) REFERENCES public.requests(id) NOT VALID;


--
-- TOC entry 2920 (class 2606 OID 16662)
-- Name: contracts Contracts_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT "Contracts_customer_id_fkey" FOREIGN KEY (customer_id) REFERENCES public.customers(id) NOT VALID;


--
-- TOC entry 2923 (class 2606 OID 16667)
-- Name: requests Requests_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT "Requests_customer_id_fkey" FOREIGN KEY (customer_id) REFERENCES public.customers(id) NOT VALID;


--
-- TOC entry 2922 (class 2606 OID 16672)
-- Name: users Users_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT "Users_role_id_fkey" FOREIGN KEY (role_id) REFERENCES public.roles(id) NOT VALID;


--
-- TOC entry 2919 (class 2606 OID 16543)
-- Name: contracts contracts_customers_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT contracts_customers_id_fk FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- TOC entry 2926 (class 2606 OID 16702)
-- Name: payments payments_accountant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_accountant_id_fkey FOREIGN KEY (accountant_id) REFERENCES public.users(id) NOT VALID;


--
-- TOC entry 2927 (class 2606 OID 16721)
-- Name: payments payments_requests_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_requests_id_fk FOREIGN KEY (request_id) REFERENCES public.requests(id);


--
-- TOC entry 2921 (class 2606 OID 16538)
-- Name: users users_roles_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_roles_id_fk FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3076 (class 0 OID 0)
-- Dependencies: 3075
-- Name: DATABASE alpaca_pj_01; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE alpaca_pj_01 TO alpaca;


-- Completed on 2021-10-04 11:35:57

--
-- PostgreSQL database dump complete
--

