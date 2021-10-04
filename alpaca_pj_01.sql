--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2021-10-01 18:50:18

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
-- TOC entry 3072 (class 1262 OID 16394)
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
-- TOC entry 209 (class 1259 OID 16641)
-- Name: AnalyzedReceipts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."AnalyzedReceipts" (
    id integer NOT NULL,
    is_valid boolean NOT NULL,
    hospital_id integer NOT NULL,
    accident_id integer NOT NULL,
    name text NOT NULL,
    amount money NOT NULL,
    analyzer_id integer NOT NULL,
    request_id integer NOT NULL
);


ALTER TABLE public."AnalyzedReceipts" OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16451)
-- Name: Contracts; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public."Contracts" (
    id integer NOT NULL,
    contract_code character(12) NOT NULL,
    customer_id integer NOT NULL,
    valid_from timestamp without time zone NOT NULL,
    valid_to timestamp without time zone NOT NULL,
    maximum_amount money NOT NULL,
    remaining_amount money NOT NULL,
    acceptable_hospital_ids integer[] NOT NULL,
    acceptable_accident_ids integer[] NOT NULL
);


ALTER TABLE public."Contracts" OWNER TO alpaca;

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
-- TOC entry 3074 (class 0 OID 0)
-- Dependencies: 203
-- Name: Contracts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public."Contracts_id_seq" OWNED BY public."Contracts".id;


--
-- TOC entry 201 (class 1259 OID 16399)
-- Name: Customers; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public."Customers" (
    id integer NOT NULL,
    full_name character varying(100) NOT NULL,
    gender boolean NOT NULL,
    id_card_number character varying(12) NOT NULL,
    phone_numbers text[] NOT NULL,
    email character varying(100) NOT NULL,
    date_of_birth date NOT NULL,
    address text NOT NULL,
    occupation character varying(50) NOT NULL
);


ALTER TABLE public."Customers" OWNER TO alpaca;

--
-- TOC entry 211 (class 1259 OID 16656)
-- Name: Payments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Payments" (
    id integer NOT NULL,
    amount money NOT NULL,
    accountant_id integer NOT NULL,
    request_id integer NOT NULL,
    payment_date timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public."Payments" OWNER TO postgres;

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
-- TOC entry 3075 (class 0 OID 0)
-- Dependencies: 210
-- Name: Payment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Payment_id_seq" OWNED BY public."Payments".id;


--
-- TOC entry 208 (class 1259 OID 16633)
-- Name: Requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Requests" (
    id integer NOT NULL,
    customer_id integer NOT NULL,
    name character varying(100) NOT NULL,
    description text NOT NULL,
    receipt_photos text[] NOT NULL,
    status character varying(20) DEFAULT 'PENDING'::character varying NOT NULL,
    close boolean DEFAULT false NOT NULL,
    employee_id integer
);


ALTER TABLE public."Requests" OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16511)
-- Name: Roles; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public."Roles" (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public."Roles" OWNER TO alpaca;

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
-- TOC entry 3076 (class 0 OID 0)
-- Dependencies: 204
-- Name: Roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public."Roles_id_seq" OWNED BY public."Roles".id;


--
-- TOC entry 206 (class 1259 OID 16519)
-- Name: Users; Type: TABLE; Schema: public; Owner: alpaca
--

CREATE TABLE public."Users" (
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
    role_id integer NOT NULL
);


ALTER TABLE public."Users" OWNER TO alpaca;

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
-- TOC entry 3077 (class 0 OID 0)
-- Dependencies: 207
-- Name: Users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public."Users_id_seq" OWNED BY public."Users".id;


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
-- TOC entry 3078 (class 0 OID 0)
-- Dependencies: 200
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: alpaca
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public."Customers".id;


--
-- TOC entry 2888 (class 2604 OID 16456)
-- Name: Contracts id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Contracts" ALTER COLUMN id SET DEFAULT nextval('public."Contracts_id_seq"'::regclass);


--
-- TOC entry 2887 (class 2604 OID 16402)
-- Name: Customers id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Customers" ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- TOC entry 2893 (class 2604 OID 16659)
-- Name: Payments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Payments" ALTER COLUMN id SET DEFAULT nextval('public."Payment_id_seq"'::regclass);


--
-- TOC entry 2889 (class 2604 OID 16514)
-- Name: Roles id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Roles" ALTER COLUMN id SET DEFAULT nextval('public."Roles_id_seq"'::regclass);


--
-- TOC entry 2890 (class 2604 OID 16524)
-- Name: Users id; Type: DEFAULT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Users" ALTER COLUMN id SET DEFAULT nextval('public."Users_id_seq"'::regclass);


--
-- TOC entry 3064 (class 0 OID 16641)
-- Dependencies: 209
-- Data for Name: AnalyzedReceipts; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3057 (class 0 OID 16451)
-- Dependencies: 202
-- Data for Name: Contracts; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3056 (class 0 OID 16399)
-- Dependencies: 201
-- Data for Name: Customers; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3066 (class 0 OID 16656)
-- Dependencies: 211
-- Data for Name: Payments; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3063 (class 0 OID 16633)
-- Dependencies: 208
-- Data for Name: Requests; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3060 (class 0 OID 16511)
-- Dependencies: 205
-- Data for Name: Roles; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3061 (class 0 OID 16519)
-- Dependencies: 206
-- Data for Name: Users; Type: TABLE DATA; Schema: public; Owner: alpaca
--



--
-- TOC entry 3079 (class 0 OID 0)
-- Dependencies: 203
-- Name: Contracts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public."Contracts_id_seq"', 1, false);


--
-- TOC entry 3080 (class 0 OID 0)
-- Dependencies: 210
-- Name: Payment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Payment_id_seq"', 1, false);


--
-- TOC entry 3081 (class 0 OID 0)
-- Dependencies: 204
-- Name: Roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public."Roles_id_seq"', 1, false);


--
-- TOC entry 3082 (class 0 OID 0)
-- Dependencies: 207
-- Name: Users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public."Users_id_seq"', 1, false);


--
-- TOC entry 3083 (class 0 OID 0)
-- Dependencies: 200
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: alpaca
--

SELECT pg_catalog.setval('public.customer_id_seq', 1, false);


--
-- TOC entry 2913 (class 2606 OID 16648)
-- Name: AnalyzedReceipts AnalyzedReceipts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."AnalyzedReceipts"
    ADD CONSTRAINT "AnalyzedReceipts_pkey" PRIMARY KEY (id);


--
-- TOC entry 2915 (class 2606 OID 16661)
-- Name: Payments Payment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Payments"
    ADD CONSTRAINT "Payment_pkey" PRIMARY KEY (id);


--
-- TOC entry 2911 (class 2606 OID 16640)
-- Name: Requests Requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Requests"
    ADD CONSTRAINT "Requests_pkey" PRIMARY KEY (id);


--
-- TOC entry 2901 (class 2606 OID 16466)
-- Name: Contracts contracts_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Contracts"
    ADD CONSTRAINT contracts_pk PRIMARY KEY (id);


--
-- TOC entry 2896 (class 2606 OID 16407)
-- Name: Customers customer_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Customers"
    ADD CONSTRAINT customer_pk PRIMARY KEY (id);


--
-- TOC entry 2904 (class 2606 OID 16517)
-- Name: Roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Roles"
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- TOC entry 2908 (class 2606 OID 16535)
-- Name: Users users_pk; Type: CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Users"
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 2898 (class 1259 OID 16463)
-- Name: contracts_contract_code_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX contracts_contract_code_uindex ON public."Contracts" USING btree (contract_code);


--
-- TOC entry 2899 (class 1259 OID 16464)
-- Name: contracts_id_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX contracts_id_uindex ON public."Contracts" USING btree (id);


--
-- TOC entry 2897 (class 1259 OID 16536)
-- Name: customers_id_card_number_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX customers_id_card_number_uindex ON public."Customers" USING btree (id_card_number);


--
-- TOC entry 2902 (class 1259 OID 16515)
-- Name: roles_id_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX roles_id_uindex ON public."Roles" USING btree (id);


--
-- TOC entry 2905 (class 1259 OID 16531)
-- Name: users_id_card_number_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_id_card_number_uindex ON public."Users" USING btree (id_card_number);


--
-- TOC entry 2906 (class 1259 OID 16532)
-- Name: users_id_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_id_uindex ON public."Users" USING btree (id);


--
-- TOC entry 2909 (class 1259 OID 16533)
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: alpaca
--

CREATE UNIQUE INDEX users_username_uindex ON public."Users" USING btree (username);


--
-- TOC entry 2921 (class 2606 OID 16677)
-- Name: AnalyzedReceipts AnalyzedReceipts_analyzer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."AnalyzedReceipts"
    ADD CONSTRAINT "AnalyzedReceipts_analyzer_id_fkey" FOREIGN KEY (analyzer_id) REFERENCES public."Users"(id) NOT VALID;


--
-- TOC entry 2922 (class 2606 OID 16682)
-- Name: AnalyzedReceipts AnalyzedReceipts_request_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."AnalyzedReceipts"
    ADD CONSTRAINT "AnalyzedReceipts_request_id_fkey" FOREIGN KEY (request_id) REFERENCES public."Requests"(id) NOT VALID;


--
-- TOC entry 2917 (class 2606 OID 16662)
-- Name: Contracts Contracts_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Contracts"
    ADD CONSTRAINT "Contracts_customer_id_fkey" FOREIGN KEY (customer_id) REFERENCES public."Customers"(id) NOT VALID;


--
-- TOC entry 2920 (class 2606 OID 16667)
-- Name: Requests Requests_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Requests"
    ADD CONSTRAINT "Requests_customer_id_fkey" FOREIGN KEY (customer_id) REFERENCES public."Customers"(id) NOT VALID;


--
-- TOC entry 2919 (class 2606 OID 16672)
-- Name: Users Users_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Users"
    ADD CONSTRAINT "Users_role_id_fkey" FOREIGN KEY (role_id) REFERENCES public."Roles"(id) NOT VALID;


--
-- TOC entry 2916 (class 2606 OID 16543)
-- Name: Contracts contracts_customers_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Contracts"
    ADD CONSTRAINT contracts_customers_id_fk FOREIGN KEY (customer_id) REFERENCES public."Customers"(id);


--
-- TOC entry 2923 (class 2606 OID 16702)
-- Name: Payments payments_accountant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Payments"
    ADD CONSTRAINT payments_accountant_id_fkey FOREIGN KEY (accountant_id) REFERENCES public."Users"(id) NOT VALID;


--
-- TOC entry 2924 (class 2606 OID 16721)
-- Name: Payments payments_requests_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Payments"
    ADD CONSTRAINT payments_requests_id_fk FOREIGN KEY (request_id) REFERENCES public."Requests"(id);


--
-- TOC entry 2918 (class 2606 OID 16538)
-- Name: Users users_roles_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: alpaca
--

ALTER TABLE ONLY public."Users"
    ADD CONSTRAINT users_roles_id_fk FOREIGN KEY (role_id) REFERENCES public."Roles"(id);


--
-- TOC entry 3073 (class 0 OID 0)
-- Dependencies: 3072
-- Name: DATABASE alpaca_pj_01; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE alpaca_pj_01 TO alpaca;


-- Completed on 2021-10-01 18:50:18

--
-- PostgreSQL database dump complete
--

