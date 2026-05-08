-- =============================================================
-- dml.sql  –  Performance-testing seed data for Shifter
-- Domain: professional business education (sales, marketing,
--         finance, leadership, HR, operations, strategy, etc.)
-- PostgreSQL only. Uses generate_series(); no literal row lists.
--
-- NOTES:
--   1. Sequence names follow Hibernate 6 / Spring Boot 3 defaults
--      ({table}_seq). Adjust setval() calls if yours differ.
--   2. admin, expert, _user extend Account (MappedSuperclass).
--      They may share one sequence (account_seq). If so, merge
--      the three setval() calls at the bottom into one.
--   3. @ElementCollection tables are included:
--        course_translation_what_will_be_learned
--        learning_path_translation_learning_outcomes
--   4. Estimated total rows: ~90 million.
--      Expected run time: 20-60 min depending on hardware.
--
-- Usage:  psql -U <user> -d <db> -f dml.sql
-- =============================================================

-- BEGIN;

SET LOCAL work_mem = '256MB';

-- =============================================================
-- SHARED: language  (2 rows)
-- =============================================================
INSERT INTO language (id, language_code, name, native_name) VALUES
                                                                (1, 'EN', 'English',    'English'),
                                                                (2, 'MK', 'Macedonian', 'Македонски')
ON CONFLICT DO NOTHING;

-- =============================================================
-- IDENTITY: admin  (10 rows, IDs 1-10)
-- =============================================================
INSERT INTO admin (id, name, email, login_provider, password_hash)
SELECT
    s,
    'Admin User ' || s,
    'admin' || s || '@shifter.com',
    'LOCAL',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVDWgFcjFi'
FROM generate_series(1, 10) s;

-- =============================================================
-- IDENTITY: expert  (1 000 rows, IDs 11-1 010)
-- Business educators, coaches, and consultants
-- =============================================================
INSERT INTO expert (id, name, email, login_provider, password_hash)
SELECT
    10 + s,
    (ARRAY[
        'Margaret','Richard','Catherine','Thomas','Elizabeth',
        'William','Patricia','Charles','Barbara','James',
        'Susan','Robert','Jessica','David','Sarah',
        'Michael','Karen','Daniel','Nancy','Mark'
        ])[1 + (s % 20)]
        || ' '
        || (ARRAY[
        'Harrison','Mitchell','Campbell','Bennett','Thornton',
        'Whitfield','Ashford','Caldwell','Pemberton','Langley',
        'Harrington','Worthington','Blackwell','Sinclair','Fitzgerald',
        'Montgomery','Chamberlain','Livingston','Wellington','Carrington'
        ])[1 + ((s / 20) % 20)],
    'expert' || s || '@shifter.com',
    CASE WHEN s % 5 = 0 THEN 'GOOGLE' ELSE 'LOCAL' END,
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVDWgFcjFi'
FROM generate_series(1, 1000) s;

-- =============================================================
-- IDENTITY: _user  (2 000 000 rows, IDs 1 011-2 001 010)
-- Business professionals across industries
-- =============================================================
INSERT INTO _user (
    id, name, email, login_provider, password_hash,
    verified, profile_complete, deleted, used_free_consultation,
    company_size, work_position, points, created_at, updated_at
)
SELECT
    1010 + s,
    (ARRAY[
        'James','Emma','Oliver','Sophia','Liam','Ava','Noah','Isabella',
        'Ethan','Mia','Lucas','Charlotte','Mason','Amelia','Logan','Harper',
        'Elijah','Evelyn','Aiden','Abigail','Jackson','Elizabeth','Grayson',
        'Scarlett','Sebastian','Victoria','Carter','Aria','Jayden','Grace'
        ])[1 + (s % 30)]
        || ' '
        || (ARRAY[
        'Smith','Johnson','Williams','Brown','Jones','Garcia','Miller',
        'Davis','Wilson','Moore','Taylor','Anderson','Thomas','Jackson',
        'White','Harris','Martin','Thompson','Young','Lee','Perez',
        'Robinson','Clark','Lewis','Hall','Allen','King','Wright',
        'Scott','Baker'
        ])[1 + ((s / 30) % 30)],
    'user' || s || '@example.com',
    CASE WHEN s % 8 = 0 THEN 'GOOGLE' ELSE 'LOCAL' END,
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVDWgFcjFi',
    (s % 10) < 8,
    (s % 5)  < 3,
    (s % 200) = 0,
    (s % 7)  = 0,
    (ARRAY['FREELANCE','MICRO','SMALL','MEDIUM','MID_MARKET','ENTERPRISE','OTHER'])[1 + (s % 7)],
    (ARRAY[
        'Sales Representative','Account Executive','Marketing Manager',
        'Brand Manager','Financial Analyst','Business Analyst',
        'HR Manager','Talent Acquisition Specialist','Operations Manager',
        'Supply Chain Coordinator','Chief Executive Officer','Chief Financial Officer',
        'Chief Marketing Officer','Vice President of Sales','Director of Operations',
        'Business Development Manager','Customer Success Manager','Product Manager',
        'Strategy Consultant','Entrepreneur'
        ])[1 + (s % 20)],
    s % 500,
    NOW() - ((s % 1095) || ' days')::INTERVAL,
    NOW() - ((s % 90)   || ' days')::INTERVAL
FROM generate_series(1, 2000000) s;

-- =============================================================
-- AUTH: verification_token  (100 000 rows)
-- =============================================================
INSERT INTO verification_token (token, user_id, created_at, expires_at)
SELECT
    gen_random_uuid(),
    1010 + s,
    NOW() - ((s % 43200) || ' seconds')::INTERVAL,
    NOW() - ((s % 43200) || ' seconds')::INTERVAL + INTERVAL '30 minutes'
FROM generate_series(1, 100000) s;

-- =============================================================
-- CATALOG: topic  (500 rows)
-- Business and professional development topic areas
-- =============================================================
INSERT INTO topic (id, slug)
SELECT
    s,
    LOWER(REPLACE(
            (ARRAY[
                'Sales','Digital Marketing','Brand Management','Content Marketing',
                'Social Media Marketing','Email Marketing','Public Relations',
                'Corporate Finance','Financial Planning','Investment Strategy',
                'Accounting','Tax Planning','Risk Management','Leadership',
                'Executive Management','Team Building','Conflict Resolution',
                'Negotiation','Business Strategy','Entrepreneurship',
                'Human Resources','Talent Management','Organisational Development',
                'Operations Management','Supply Chain','Procurement',
                'Customer Service','Customer Experience','Business Communication',
                'Presentation Skills','Project Management','Change Management',
                'Business Law','Compliance','Corporate Governance',
                'Data-Driven Marketing','Market Research','Pricing Strategy',
                'Retail Management','E-Commerce','Business Development',
                'Mergers and Acquisitions','Private Equity','Venture Capital',
                'Real Estate Business','Insurance','Business Analytics',
                'Sustainability','Corporate Social Responsibility','Strategic Partnerships'
                ])[1 + ((s-1) % 50)],
            ' ', '-')) || '-' || ((s-1)/50 + 1)
FROM generate_series(1, 500) s;

-- topic_translation  (1 000 rows – 2 per topic)
INSERT INTO topic_translation (id, name, description, created_at, topic_id, language_id)
SELECT
    s,
    (ARRAY[
        'Sales','Digital Marketing','Brand Management','Content Marketing',
        'Social Media Marketing','Email Marketing','Public Relations',
        'Corporate Finance','Financial Planning','Investment Strategy',
        'Accounting','Tax Planning','Risk Management','Leadership',
        'Executive Management','Team Building','Conflict Resolution',
        'Negotiation','Business Strategy','Entrepreneurship',
        'Human Resources','Talent Management','Organisational Development',
        'Operations Management','Supply Chain','Procurement',
        'Customer Service','Customer Experience','Business Communication',
        'Presentation Skills','Project Management','Change Management',
        'Business Law','Compliance','Corporate Governance',
        'Data-Driven Marketing','Market Research','Pricing Strategy',
        'Retail Management','E-Commerce','Business Development',
        'Mergers and Acquisitions','Private Equity','Venture Capital',
        'Real Estate Business','Insurance','Business Analytics',
        'Sustainability','Corporate Social Responsibility','Strategic Partnerships'
        ])[1 + (((s-1)/2) % 50)],
    'Develop your expertise in '
        || LOWER((ARRAY[
        'Sales','Digital Marketing','Brand Management','Content Marketing',
        'Social Media Marketing','Email Marketing','Public Relations',
        'Corporate Finance','Financial Planning','Investment Strategy',
        'Accounting','Tax Planning','Risk Management','Leadership',
        'Executive Management','Team Building','Conflict Resolution',
        'Negotiation','Business Strategy','Entrepreneurship',
        'Human Resources','Talent Management','Organisational Development',
        'Operations Management','Supply Chain','Procurement',
        'Customer Service','Customer Experience','Business Communication',
        'Presentation Skills','Project Management','Change Management',
        'Business Law','Compliance','Corporate Governance',
        'Data-Driven Marketing','Market Research','Pricing Strategy',
        'Retail Management','E-Commerce','Business Development',
        'Mergers and Acquisitions','Private Equity','Venture Capital',
        'Real Estate Business','Insurance','Business Analytics',
        'Sustainability','Corporate Social Responsibility','Strategic Partnerships'
        ])[1 + (((s-1)/2) % 50)])
        || ' with courses taught by seasoned industry practitioners.',
    NOW() - ((s % 500) || ' days')::INTERVAL,
    (s + 1) / 2,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END
FROM generate_series(1, 1000) s;

-- =============================================================
-- CATALOG: skill  (1 000 rows)
-- Concrete professional and business skills
-- =============================================================
INSERT INTO skill (id, slug, show_in_radar)
SELECT
    s,
    LOWER(REPLACE(
            (ARRAY[
                'Cold Calling','B2B Sales','B2C Sales','Account Management',
                'Sales Pipeline Management','CRM Software','Lead Generation',
                'Sales Forecasting','Contract Negotiation','Closing Techniques',
                'SEO & SEM','Pay-Per-Click Advertising','Social Media Strategy',
                'Email Campaigns','Content Creation','Copywriting','Brand Positioning',
                'Market Segmentation','Consumer Behaviour Analysis','Campaign Management',
                'Financial Modelling','Budgeting & Forecasting','Cash Flow Management',
                'Balance Sheet Analysis','Profit & Loss Reporting','Cost Reduction',
                'Capital Allocation','Valuation Methods','Financial Risk Assessment',
                'Audit & Compliance','Strategic Planning','Competitive Analysis',
                'SWOT Analysis','Business Case Development','OKR Setting',
                'KPI Tracking','Stakeholder Management','Executive Presentation',
                'Active Listening','Persuasion & Influence','Conflict Mediation',
                'Performance Management','Recruitment & Selection','Onboarding Design',
                'Employee Engagement','Compensation & Benefits','Labour Law',
                'Process Optimisation','Lean Management','Six Sigma',
                'Inventory Management','Logistics Coordination','Vendor Management',
                'Customer Retention','Net Promoter Score','Service Recovery',
                'Project Scheduling','Risk Register','Agile for Business',
                'Change Communication','Organisational Culture','Business Ethics',
                'GDPR Compliance','Corporate Reporting','ESG Strategy',
                'E-Commerce Operations','Marketplace Strategy','Pricing Analytics',
                'Revenue Management','P&L Ownership','Cross-Functional Leadership'
                ])[1 + ((s-1) % 70)],
            ' ', '-')) || '-v' || ((s-1)/70 + 1),
    (s % 3) < 2
FROM generate_series(1, 1000) s;

-- skill_translation  (2 000 rows)
INSERT INTO skill_translation (id, name, description, created_at, skill_id, language_id)
SELECT
    s,
    (ARRAY[
        'Cold Calling','B2B Sales','B2C Sales','Account Management',
        'Sales Pipeline Management','CRM Software','Lead Generation',
        'Sales Forecasting','Contract Negotiation','Closing Techniques',
        'SEO & SEM','Pay-Per-Click Advertising','Social Media Strategy',
        'Email Campaigns','Content Creation','Copywriting','Brand Positioning',
        'Market Segmentation','Consumer Behaviour Analysis','Campaign Management',
        'Financial Modelling','Budgeting & Forecasting','Cash Flow Management',
        'Balance Sheet Analysis','Profit & Loss Reporting','Cost Reduction',
        'Capital Allocation','Valuation Methods','Financial Risk Assessment',
        'Audit & Compliance','Strategic Planning','Competitive Analysis',
        'SWOT Analysis','Business Case Development','OKR Setting',
        'KPI Tracking','Stakeholder Management','Executive Presentation',
        'Active Listening','Persuasion & Influence','Conflict Mediation',
        'Performance Management','Recruitment & Selection','Onboarding Design',
        'Employee Engagement','Compensation & Benefits','Labour Law',
        'Process Optimisation','Lean Management','Six Sigma',
        'Inventory Management','Logistics Coordination','Vendor Management',
        'Customer Retention','Net Promoter Score','Service Recovery',
        'Project Scheduling','Risk Register','Agile for Business',
        'Change Communication','Organisational Culture','Business Ethics',
        'GDPR Compliance','Corporate Reporting','ESG Strategy',
        'E-Commerce Operations','Marketplace Strategy','Pricing Analytics',
        'Revenue Management','P&L Ownership','Cross-Functional Leadership'
        ])[1 + (((s-1)/2) % 70)],
    'Strengthen your ability to '
        || LOWER((ARRAY[
        'close more deals through disciplined cold outreach',
        'manage complex B2B relationships and enterprise accounts',
        'drive consumer sales with persuasive techniques',
        'grow and retain a portfolio of key accounts',
        'build and manage a predictable sales pipeline',
        'leverage CRM tools to track and convert leads',
        'generate qualified leads through inbound and outbound channels',
        'produce accurate revenue forecasts to guide business planning',
        'negotiate contracts that protect margin and build partnerships',
        'apply proven closing techniques to win more business',
        'drive organic and paid traffic through search engine strategies',
        'design and optimise pay-per-click campaigns for maximum ROI',
        'build a social media presence that converts followers to customers',
        'craft email campaigns that nurture leads and drive conversions',
        'produce compelling content that attracts and retains audiences',
        'write persuasive copy for ads, landing pages, and emails',
        'define and communicate a differentiated brand position',
        'identify and target the most valuable market segments',
        'analyse consumer behaviour to inform marketing decisions',
        'plan, execute, and optimise end-to-end marketing campaigns',
        'build sophisticated financial models to support decision-making',
        'produce accurate budgets and rolling forecasts',
        'manage cash flow to ensure business liquidity',
        'read and interpret balance sheets with confidence',
        'produce and analyse profit and loss statements',
        'identify and implement cost-reduction initiatives',
        'allocate capital effectively across competing priorities',
        'value businesses and assets using established methodologies',
        'identify, quantify, and mitigate financial risk',
        'ensure compliance with accounting standards and audit requirements',
        'develop long-term strategic plans aligned to company goals',
        'conduct competitive analysis to identify market opportunities',
        'apply SWOT analysis to evaluate strategic options',
        'build compelling business cases to gain executive buy-in',
        'set and cascade OKRs across the organisation',
        'define, track, and act on key performance indicators',
        'manage stakeholders across all organisational levels',
        'deliver high-impact executive presentations with confidence',
        'practice active listening to improve communication outcomes',
        'influence decisions and behaviours through principled persuasion',
        'mediate workplace conflicts constructively and fairly',
        'set clear performance expectations and conduct effective reviews',
        'attract and select top talent through structured hiring processes',
        'design onboarding programmes that accelerate new-hire productivity',
        'measure and improve employee engagement across the organisation',
        'design fair, competitive compensation and benefits packages',
        'apply labour law principles to HR practice and policy',
        'map and optimise business processes to eliminate waste',
        'apply lean management principles to improve operational efficiency',
        'use Six Sigma methods to reduce defects and variation',
        'manage inventory levels to balance cost and service levels',
        'coordinate logistics and transportation to meet delivery targets',
        'select and manage vendors to deliver quality and value',
        'design programmes that improve customer retention and loyalty',
        'measure customer satisfaction using Net Promoter Score methodology',
        'recover effectively from service failures to retain customers',
        'build and maintain project schedules that keep teams on track',
        'identify and manage project risks before they become issues',
        'apply Agile principles in non-technical business contexts',
        'communicate change initiatives in ways that build commitment',
        'shape organisational culture to support strategic goals',
        'apply ethical frameworks to complex business decisions',
        'ensure data handling practices comply with GDPR requirements',
        'produce clear, accurate corporate reports for stakeholders',
        'develop and execute an ESG strategy that creates real value',
        'run efficient e-commerce operations at scale',
        'build a profitable presence on digital marketplaces',
        'use pricing analytics to maximise revenue and margin',
        'optimise revenue streams through dynamic pricing and yield management',
        'own a P&L and make decisions that protect and grow profitability',
        'lead cross-functional teams towards shared commercial objectives'
        ])[1 + (((s-1)/2) % 70)])
                     || '.',
                 NOW() - ((s % 365) || ' days')::INTERVAL,
                 (s + 1) / 2,
                 CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END
                 FROM generate_series(1, 2000) s;

-- =============================================================
-- CATALOG: course  (100 000 rows)
-- =============================================================
INSERT INTO course (id, image_url, color, difficulty, duration_minutes)
SELECT
    s,
    'https://cdn.shifter.com/courses/' || s || '/thumbnail.jpg',
    (ARRAY[
        '#1D4ED8','#7C3AED','#047857','#B45309','#B91C1C',
        '#0E7490','#4D7C0F','#C2410C','#BE185D','#0F766E'
        ])[1 + (s % 10)],
    (ARRAY['BEGINNER','INTERMEDIATE','ADVANCED','EXPERT'])[1 + (s % 4)],
    45 + (s % 375)
FROM generate_series(1, 100000) s;

-- course_version  (200 000 rows – 2 per course)
INSERT INTO course_version (id, version_number, creation_date, active, course_id)
SELECT
    s,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    CURRENT_DATE - ((CASE WHEN s % 2 = 1 THEN 365 ELSE 30 END + s % 100) || ' days')::INTERVAL,
    (s % 2 = 0),
    (s + 1) / 2
FROM generate_series(1, 200000) s;

-- course_price  (100 000 rows – 1 per course)
INSERT INTO course_price (
    id, amount, active, discounted,
    discount_amount, discount_percentage, created_at, course_id
)
SELECT
    s,
    (ARRAY[29,39,49,59,69,79,89,99,109,129,149,179,199,249,299])[1 + (s % 15)]::DECIMAL,
    true,
    (s % 3 = 0),
    CASE WHEN s % 3 = 0 THEN (ARRAY[10,15,20,25,30,40])[1 + (s % 6)]::DECIMAL ELSE 0 END,
    CASE WHEN s % 3 = 0 THEN (ARRAY[10,15,20,25,30,40])[1 + (s % 6)]::DECIMAL ELSE 0 END,
    NOW() - ((s % 730) || ' days')::INTERVAL,
    s
FROM generate_series(1, 100000) s;

-- course_translation  (200 000 rows – 2 per course)
INSERT INTO course_translation (
    id, title_short, title, description_short, description, description_long,
    created_at, language_id, course_id
)
SELECT
    s,
    -- title_short
    (ARRAY[
        'Sales Fundamentals','B2B Sales Mastery','Winning Negotiations',
        'Digital Marketing','Email Marketing Pro','Brand Strategy',
        'Financial Planning','Corporate Finance','Management Accounting',
        'Leadership Excellence','Executive Leadership','Team Management',
        'HR Essentials','Talent Acquisition','Performance Management',
        'Operations Management','Supply Chain','Lean & Six Sigma',
        'Business Strategy','Entrepreneurship Bootcamp'
        ])[1 + (((s-1)/2) % 20)]
        || ' ' || ((s-1)/2 + 1),
    -- title
    'The Complete '
        || (ARRAY[
        'Sales Fundamentals','B2B Sales','Negotiation',
        'Digital Marketing','Email Marketing','Brand Strategy',
        'Financial Planning','Corporate Finance','Management Accounting',
        'Leadership','Executive Leadership','Team Management',
        'Human Resources','Talent Acquisition','Performance Management',
        'Operations Management','Supply Chain Management','Lean & Six Sigma',
        'Business Strategy','Entrepreneurship'
        ])[1 + (((s-1)/2) % 20)]
        || ' Course: From Principles to Practice',
    -- description_short
    'Master '
        || LOWER((ARRAY[
        'sales fundamentals','B2B sales','negotiation tactics',
        'digital marketing','email marketing','brand strategy',
        'financial planning','corporate finance','management accounting',
        'leadership skills','executive leadership','team management',
        'HR essentials','talent acquisition','performance management',
        'operations management','supply chain management','lean and Six Sigma',
        'business strategy','entrepreneurship'
        ])[1 + (((s-1)/2) % 20)])
        || ' with expert coaching and real-world case studies.',
    -- description
    'This course gives you the frameworks, tools, and confidence to excel in '
        || LOWER((ARRAY[
        'sales and revenue generation','B2B account management and pipeline building',
        'high-stakes negotiations that protect your interests',
        'end-to-end digital marketing strategy and execution',
        'email marketing that nurtures leads and drives revenue',
        'building and managing a powerful brand',
        'personal and corporate financial planning',
        'corporate finance decisions and capital allocation',
        'management accounting and business performance reporting',
        'leading teams and organisations with purpose and clarity',
        'C-suite leadership and boardroom effectiveness',
        'building, motivating, and retaining high-performing teams',
        'HR policies, employment law, and people strategy',
        'recruiting, selecting, and onboarding top talent',
        'setting goals, reviewing performance, and developing people',
        'operational efficiency and continuous improvement',
        'supply chain design, sourcing, and logistics',
        'lean management and Six Sigma quality methods',
        'competitive strategy and strategic planning processes',
        'launching and scaling a profitable business'
        ])[1 + (((s-1)/2) % 20)])
        || '. Taught by practitioners with decades of real industry experience.',
    -- description_long
    'This in-depth programme is designed for professionals who want to move beyond theory and apply '
        || LOWER((ARRAY[
        'proven sales methodologies','enterprise sales techniques',
        'principled negotiation frameworks','integrated digital marketing strategies',
        'data-driven email marketing approaches','strategic brand management principles',
        'comprehensive financial planning tools','corporate finance techniques',
        'management accounting and cost-control methods','transformational leadership practices',
        'executive presence and strategic decision-making','people management and team dynamics',
        'modern HR practices and employment relations','structured talent acquisition processes',
        'evidence-based performance management systems','operational excellence frameworks',
        'end-to-end supply chain management practices','lean and Six Sigma quality systems',
        'rigorous business strategy frameworks','entrepreneurial growth models'
        ])[1 + (((s-1)/2) % 20)])
        || ' in their day-to-day roles. You will analyse real case studies, complete practical exercises, and earn a certificate of completion.',
    NOW() - ((s % 365) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 200000) s;

-- @ElementCollection: course_translation_what_will_be_learned
-- (3 per translation × 200 000 = 600 000 rows)
INSERT INTO course_translation_what_will_be_learned (
    course_translation_id, what_will_be_learned
)
SELECT
    ((s - 1) / 3) + 1,
    (ARRAY[
        'Apply a repeatable, structured process to win more business',
        'Analyse financial statements and make data-driven decisions',
        'Lead difficult conversations and negotiations with confidence',
        'Design and execute marketing campaigns that deliver measurable ROI',
        'Build a high-performance culture within your team or organisation',
        'Identify and exploit strategic opportunities before competitors do',
        'Manage operations efficiently and eliminate costly waste',
        'Attract, develop, and retain top talent in a competitive market',
        'Communicate your ideas persuasively to any audience',
        'Earn a certificate recognised by leading employers'
        ])[1 + (s % 10)]
FROM generate_series(1, 600000) s;

-- =============================================================
-- CATALOG: course_module  (1 000 000 rows – 5 per course_version)
-- =============================================================
INSERT INTO course_module (id, position, course_version_id)
SELECT
    s,
    1 + ((s - 1) % 5),
    (s - 1) / 5 + 1
FROM generate_series(1, 1000000) s;

-- course_module_translation  (2 000 000 rows – 2 per module)
INSERT INTO course_module_translation (id, title, created_at, language_id, course_module_id)
SELECT
    s,
    (ARRAY[
        'Foundations & Context','Core Frameworks','Applied Techniques',
        'Real-World Case Studies','Assessment & Certification',
        'Getting Started','Key Principles','Practical Skill-Building',
        'Workplace Application','Review, Reflection & Next Steps'
        ])[1 + (((s-1)/2) % 10)],
    NOW() - ((s % 300) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 2000000) s;

-- =============================================================
-- CATALOG: course_lecture  (4 000 000 rows – 4 per module)
-- =============================================================
INSERT INTO course_lecture (id, duration_minutes, position, content_type, course_module_id)
SELECT
    s,
    5 + (s % 55),
    1 + ((s - 1) % 4),
    (ARRAY['VIDEO','TEXT','FILE','QUIZ','TOOL'])[1 + (s % 5)],
    (s - 1) / 4 + 1
FROM generate_series(1, 4000000) s;

-- course_lecture_translation  (8 000 000 rows – 2 per lecture)
INSERT INTO course_lecture_translation (
    id, content_file_name, title, description, content_text,
    created_at, language_id, course_lecture_id
)
SELECT
    s,
    CASE WHEN (s % 5) IN (0,2) THEN NULL
         ELSE 'lecture-' || ((s+1)/2) || '.mp4'
        END,
    (ARRAY[
        'Why This Matters: The Business Case',
        'Core Concepts Every Professional Must Know',
        'Frameworks Used by Industry Leaders',
        'Step-by-Step Walkthrough with Examples',
        'Common Pitfalls and How to Avoid Them',
        'Applying This in Your Organisation',
        'Real Case Study: What Worked and Why',
        'Interactive Exercise: Practise the Skill',
        'Checklist and Action Plan',
        'Expert Interview: Lessons from the Field'
        ])[1 + (((s-1)/2) % 10)],
    'In this lecture you will '
        || (ARRAY[
        'understand why this skill is critical to career advancement',
        'learn the key concepts and terminology used by practitioners',
        'explore the frameworks that leading companies use in practice',
        'follow a worked example from start to finish',
        'identify the most common mistakes and how to sidestep them',
        'discover how to apply this in your specific business context',
        'analyse a real-world case study and draw actionable insights',
        'practise the skill through a guided exercise with feedback',
        'leave with a clear checklist and 30-day action plan',
        'hear first-hand insights from an experienced industry leader'
        ])[1 + (((s-1)/2) % 10)]
        || '.',
    NULL,
    NOW() - ((s % 200) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 8000000) s;

-- =============================================================
-- CATALOG: join tables
-- =============================================================

-- course_topic  (~200 000 pairs – 2 per course)
INSERT INTO course_topic (course_id, topic_id)
SELECT s, 1 + (s % 500)           FROM generate_series(1, 100000) s
UNION
SELECT s, 1 + ((s + 250) % 500)   FROM generate_series(1, 100000) s
ON CONFLICT DO NOTHING;

-- course_skill  (~200 000 pairs – 2 per course)
INSERT INTO course_skill (course_id, skill_id)
SELECT s, 1 + (s % 1000)          FROM generate_series(1, 100000) s
UNION
SELECT s, 1 + ((s + 500) % 1000)  FROM generate_series(1, 100000) s
ON CONFLICT DO NOTHING;

-- expert_course  (~100 000 pairs)
INSERT INTO expert_course (expert_id, course_id)
SELECT DISTINCT
    10 + (s % 1000) + 1,
    1  + ((s * 97) % 100000)
FROM generate_series(1, 100000) s
ON CONFLICT DO NOTHING;

-- related_course  (200 000 rows)
INSERT INTO related_course (id, similarity_score, calculated_at, course_id, related_course_id)
SELECT
    s,
    (0.50 + (s % 50) * 0.01)::DECIMAL,
    NOW() - ((s % 30) || ' days')::INTERVAL,
    LEAST(   1 + (s % 100000), 1 + ((s * 37 + 13) % 100000)),
    GREATEST(1 + (s % 100000), 1 + ((s * 37 + 13) % 100000))
FROM generate_series(1, 200000) s
WHERE (s % 100000) <> ((s * 37 + 13) % 100000)
ON CONFLICT DO NOTHING;

-- =============================================================
-- ASSESSMENT: quiz  (100 000 rows – 1 per active course_version)
-- Active course_version IDs are even: 2, 4, … 200 000
-- =============================================================
INSERT INTO quiz (
    id, type, passing_score, randomized, active, created_at,
    course_module_id, course_version_id
)
SELECT
    s,
    (ARRAY['PRE_DIAGNOSTIC','CHECKPOINT','FINAL'])[1 + (s % 3)],
    60 + (s % 30),
    (s % 4 = 0),
    true,
    NOW() - ((s % 365) || ' days')::INTERVAL,
    NULL,
    s * 2
FROM generate_series(1, 100000) s;

-- quiz_translation  (200 000 rows – 2 per quiz)
INSERT INTO quiz_translation (id, title, description, created_at, language_id, quiz_id)
SELECT
    s,
    (ARRAY[
        'Pre-Programme Knowledge Check',
        'Module Progress Assessment',
        'Final Certification Exam',
        'Practical Skills Evaluation',
        'Business Acumen Assessment',
        'Leadership Competency Review',
        'Commercial Awareness Test',
        'Strategic Thinking Evaluation',
        'Functional Knowledge Quiz',
        'Competency Benchmark Assessment'
        ])[1 + (((s-1)/2) % 10)],
    'Test your understanding of the material covered and confirm you are ready to apply these skills in your professional role.',
    NOW() - ((s % 300) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 200000) s;

-- quiz_question  (1 000 000 rows – 10 per quiz)
INSERT INTO quiz_question (id, position, points, type, quiz_id)
SELECT
    s,
    1 + ((s - 1) % 10),
    (ARRAY[1,2,3,5])[1 + (s % 4)],
    (ARRAY['SINGLE_CHOICE','MULTIPLE_CHOICE','TRUE_FALSE'])[1 + (s % 3)],
    (s - 1) / 10 + 1
FROM generate_series(1, 1000000) s;

-- quiz_question_translation  (2 000 000 rows – 2 per question)
INSERT INTO quiz_question_translation (
    id, question_text, scenario, created_at, language_id, quiz_question_id
)
SELECT
    s,
    (ARRAY[
        'Which approach is considered best practice when entering a new market segment?',
        'A client objects that your price is too high. What is the most effective response?',
        'True or False: A positive gross margin guarantees a profitable business.',
        'Which of the following is the most reliable indicator of sales team performance?',
        'A new competitor has entered your market with a lower price. What should you do first?',
        'Which financial metric best measures the efficiency of working capital management?',
        'Select all the characteristics of an effective performance review conversation.',
        'Your team is resistant to a major organisational change. What is the first step?',
        'Which procurement strategy is most appropriate for a single-source critical supplier?',
        'A customer threatens to cancel their contract. What is the most appropriate first action?'
        ])[1 + (((s-1)/2) % 10)],
    CASE WHEN s % 7 = 0
             THEN 'A mid-size retail company with £50 million in annual revenue is reviewing its commercial strategy. '
         ELSE NULL
        END,
    NOW() - ((s % 200) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 2000000) s;

-- quiz_answer_option  (4 000 000 rows – 4 per question)
-- Position 0 mod 4 is always the correct option
INSERT INTO quiz_answer_option (id, correct, quiz_question_id)
SELECT
    s,
    (s % 4 = 0),
    (s - 1) / 4 + 1
FROM generate_series(1, 4000000) s;

-- quiz_answer_option_translation  (8 000 000 rows – 2 per option)
INSERT INTO quiz_answer_option_translation (
    id, answer_text, explanation, created_at, language_id, quiz_answer_option_id
)
SELECT
    s,
    (ARRAY[
        'Conduct a thorough analysis of customer needs and competitive positioning before committing resources.',
        'Launch immediately with the lowest price to capture market share quickly.',
        'Replicate the market leader''s strategy to reduce execution risk.',
        'Delay entry until the market is fully mature and risks are minimised.',
        'Acknowledge the concern, quantify the value you deliver, and reinforce ROI.',
        'Immediately offer a discount to avoid losing the deal.',
        'Escalate to your manager so they can handle the price negotiation.',
        'Agree with the customer and promise to match any competitor price.',
        'False — a business can have a positive gross margin and still be unprofitable if overheads exceed it.',
        'True — gross margin is the single most important profitability metric for any business.',
        'Revenue attainment against target, alongside pipeline coverage and conversion rates.',
        'The number of calls made per day, regardless of outcomes.',
        'Analyse your own value proposition and differentiate on quality, service, or relationship.',
        'Match the competitor''s price immediately to protect market share.',
        'Exit the market segment rather than compete on price.',
        'Ignore the competitor and hope customers remain loyal.',
        'Cash conversion cycle — it measures how efficiently a company converts investment into cash flow.',
        'Return on equity, because it reflects overall financial health.',
        'EBITDA margin, because it excludes all non-operational costs.',
        'Revenue growth rate, as it shows the business is expanding.'
        ])[1 + (((s-1)/2) % 20)],
    CASE WHEN (((s-1)/2) % 4) = 0
             THEN 'Correct. This approach reflects best practice as taught in the course and is supported by the research evidence presented.'
         ELSE 'Incorrect. While plausible, this option overlooks a critical factor covered in the course material. Review the relevant module before retaking the assessment.'
        END,
    NOW() - ((s % 180) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 8000000) s;

-- quiz_question_skill  (1 000 000 rows – 1 per question)
INSERT INTO quiz_question_skill (id, proficiency, weight, quiz_question_id, skill_id)
SELECT
    s,
    (ARRAY['BEGINNER','INTERMEDIATE','ADVANCED','EXPERT'])[1 + (s % 4)],
    1 + (s % 5),
    s,
    1 + (s % 1000)
FROM generate_series(1, 1000000) s;

-- =============================================================
-- LEARNING: enrollment  (10 000 000 rows)
-- =============================================================
INSERT INTO enrollment (
    id, enrollment_status, enrollment_type, on_trial,
    enrollment_date, purchase_date, activation_date, completion_date,
    updated_at, course_version_id, user_id
)
SELECT
    s,
    CASE
        WHEN s % 10 < 2 THEN 'PENDING'
        WHEN s % 10 < 7 THEN 'ACTIVE'
        ELSE 'COMPLETED'
        END,
    (ARRAY['INDIVIDUAL','BUNDLE','LEARNING_PATH'])[1 + (s % 3)],
    (s % 15 = 0),
    CURRENT_DATE - ((s % 730) || ' days')::INTERVAL,
    CASE WHEN s % 10 >= 2
             THEN CURRENT_DATE - ((s % 720) || ' days')::INTERVAL ELSE NULL END,
    CASE WHEN s % 10 >= 2
             THEN CURRENT_DATE - ((s % 715) || ' days')::INTERVAL ELSE NULL END,
    CASE WHEN s % 10 >= 7
             THEN CURRENT_DATE - ((s % 300) || ' days')::INTERVAL ELSE NULL END,
    NOW() - ((s % 30) || ' days')::INTERVAL,
    2 * (1 + (s % 100000)),
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 10000000) s;

-- =============================================================
-- LEARNING: lecture_progress  (12 000 000 rows)
-- =============================================================
INSERT INTO lecture_progress (id, completed, completed_at, enrollment_id, course_lecture_id)
SELECT
    s,
    (s % 8) < 6,
    CASE WHEN (s % 8) < 6
             THEN NOW() - ((s % 365) || ' days')::INTERVAL
         ELSE NOW()
        END,
    1 + (s % 10000000),
    1 + (s % 4000000)
FROM generate_series(1, 12000000) s;

-- =============================================================
-- ASSESSMENT: quiz_attempt  (3 000 000 rows)
-- =============================================================
INSERT INTO quiz_attempt (
    id, attempt_number, started_at, completed_at, status,
    score, total_points, earned_points, passed, quiz_id, enrollment_id
)
SELECT
    s,
    1 + (s % 3),
    NOW() - ((s % 365) || ' days')::INTERVAL,
    NOW() - ((s % 365) || ' days')::INTERVAL + INTERVAL '30 minutes',
    (ARRAY['PASSED','PASSED','FAILED','ABANDONED'])[1 + (s % 4)],
    50 + (s % 50),
    100,
    50 + (s % 50),
    (50 + (s % 50)) >= 70,
    1 + (s % 100000),
    1 + (s % 10000000)
FROM generate_series(1, 3000000) s;

-- quiz_attempt_answer  (6 000 000 rows – 2 per attempt)
INSERT INTO quiz_attempt_answer (id, correct, quiz_question_id, quiz_attempt_id)
SELECT
    s,
    (s % 3) < 2,
    1 + (s % 1000000),
    (s - 1) / 2 + 1
FROM generate_series(1, 6000000) s;

-- quiz_attempt_answer_selected_options  (~9 000 000 rows)
INSERT INTO quiz_attempt_answer_selected_options (quiz_attempt_answer_id, quiz_answer_option_id)
SELECT s, 1 + (s % 4000000)
FROM generate_series(1, 6000000) s
UNION ALL
SELECT s, 1 + ((s + 2000000) % 4000000)
FROM generate_series(1, 3000000) s;

-- =============================================================
-- LEARNING: user_skill  (2 000 000 rows)
-- =============================================================
INSERT INTO user_skill (
    id, verified, proficiency, proficiency_score,
    created_at, updated_at, enrollment_id, skill_id, user_id
)
SELECT
    s,
    (s % 5) < 3,
    (ARRAY['BEGINNER','INTERMEDIATE','ADVANCED','EXPERT'])[1 + (s % 4)],
    10 + (s % 90),
    NOW() - ((s % 365) || ' days')::INTERVAL,
    NOW() - ((s % 30)  || ' days')::INTERVAL,
    1 + (s % 10000000),
    1 + (s % 1000),
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 2000000) s;

-- user_skill_snapshot  (1 000 000 rows)
INSERT INTO user_skill_snapshot (
    id, proficiency_at_time, proficiency_score_at_time,
    new_proficiency, new_proficiency_score, created_at,
    quiz_attempt_id, enrollment_id, user_skill_id
)
SELECT
    s,
    (ARRAY['BEGINNER','INTERMEDIATE','ADVANCED','EXPERT'])[1 + (s % 4)],
    10 + (s % 60),
    (ARRAY['BEGINNER','INTERMEDIATE','ADVANCED','EXPERT'])[1 + ((s + 1) % 4)],
    20 + (s % 70),
    NOW() - ((s % 300) || ' days')::INTERVAL,
    1 + (s % 3000000),
    1 + (s % 10000000),
    1 + (s % 2000000)
FROM generate_series(1, 1000000) s;

-- =============================================================
-- LEARNING: review  (500 000 rows)
-- Linked to enrollment IDs 1..500 000
-- =============================================================
INSERT INTO review (id, rating, comment, date, enrollment_id)
SELECT
    s,
    3 + (s % 3),
    (ARRAY[
        'Excellent course — immediately applicable to my day-to-day role.',
        'Incredibly well structured. The case studies made it come alive.',
        'The instructor brought real credibility and practical insight.',
        'Good content overall. A few modules could go deeper.',
        'This course helped me land a promotion within three months.',
        'Worth every penny. I have recommended it to my entire team.',
        'Some sections felt a bit theoretical — more exercises would help.',
        NULL
        ])[1 + (s % 8)],
    CURRENT_DATE - ((s % 600) || ' days')::INTERVAL,
    s
FROM generate_series(1, 500000) s;

-- =============================================================
-- LEARNING: certificate  (300 000 rows)
-- Linked to enrollment IDs 500 001..800 000
-- =============================================================
INSERT INTO certificate (
    id, issue_date, certificate_url, certificate_number, user_id, enrollment_id
)
SELECT
    s,
    CURRENT_DATE - ((s % 500) || ' days')::INTERVAL,
    'https://cdn.shifter.com/certificates/CERT-' || LPAD(s::TEXT, 8, '0') || '.pdf',
    'CERT-' || LPAD(s::TEXT, 8, '0'),
    1010 + 1 + (s % 2000000),
    500000 + s
FROM generate_series(1, 300000) s;

-- =============================================================
-- COMMERCE: _order  (1 000 000 rows)
-- =============================================================
INSERT INTO _order (id, status, created_at)
SELECT
    s,
    (ARRAY['COMPLETED','COMPLETED','COMPLETED','PENDING','CANCELLED'])[1 + (s % 5)],
    NOW() - ((s % 730) || ' days')::INTERVAL
FROM generate_series(1, 1000000) s;

-- payment  (1 000 000 rows)
INSERT INTO payment (id, amount, date, method, status, order_id)
SELECT
    s,
    (29 + (s % 270))::DOUBLE PRECISION,
    CURRENT_DATE - ((s % 730) || ' days')::INTERVAL,
    (ARRAY['CARD','PAYPAL','CASYS'])[1 + (s % 3)],
    (ARRAY['COMPLETED','COMPLETED','COMPLETED','PENDING','FAILED'])[1 + (s % 5)],
    s
FROM generate_series(1, 1000000) s;

-- order_details  (1 000 000 rows)
-- Linked to enrollment IDs 800 001..1 800 000
INSERT INTO order_details (
    id, price, discount_amount, discount_percentage,
    created_at, order_id, enrollment_id, course_id
)
SELECT
    s,
    (29 + (s % 270))::DECIMAL,
    CASE WHEN s % 3 = 0 THEN (s % 40)::DECIMAL ELSE 0 END,
    CASE WHEN s % 3 = 0 THEN (10 + (s % 40))::DECIMAL ELSE 0 END,
    NOW() - ((s % 730) || ' days')::INTERVAL,
    s,
    800000 + s,
    1 + (s % 100000)
FROM generate_series(1, 1000000) s;

-- =============================================================
-- CONSULTATION: meeting_email_reminder  (200 000 rows)
-- =============================================================
INSERT INTO meeting_email_reminder (
    id, created_at, updated_at, meeting_at, scheduled_at,
    sent, status, meeting_link, user_id
)
SELECT
    s,
    NOW() - ((s % 365) || ' days')::INTERVAL,
    NOW() - ((s % 30)  || ' days')::INTERVAL,
    NOW() + ((s % 60)  || ' days')::INTERVAL,
    NOW() + ((s % 60)  || ' days')::INTERVAL - INTERVAL '1 hour',
    (s % 3) < 2,
    (ARRAY['SENT','SENT','PENDING','FAILED'])[1 + (s % 4)],
    'https://zoom.us/j/' || (1000000000 + s),
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 200000) s;

-- =============================================================
-- COLLECTION: learning_path  (10 000 rows)
-- =============================================================
INSERT INTO learning_path (
    id, type, slug, image_url, base_price, discounted,
    discount_amount, discount_percentage, estimated_duration_hours,
    difficulty, active, created_at
)
SELECT
    s,
    (ARRAY['SYSTEM_GENERATED','CURATED','CURATED','PERSONALIZED'])[1 + (s % 4)],
    'learning-path-' || s,
    'https://cdn.shifter.com/learning-paths/' || s || '/cover.jpg',
    (99 + (s % 300))::DECIMAL,
    (s % 3 = 0),
    CASE WHEN s % 3 = 0 THEN (15 + (s % 50))::DECIMAL ELSE 0 END,
    CASE WHEN s % 3 = 0 THEN (15 + (s % 30))::DECIMAL ELSE 0 END,
    20 + (s % 80),
    (ARRAY['BEGINNER','INTERMEDIATE','ADVANCED','EXPERT'])[1 + (s % 4)],
    (s % 20) < 18,
    NOW() - ((s % 730) || ' days')::INTERVAL
FROM generate_series(1, 10000) s;

-- learning_path_translation  (20 000 rows – 2 per path)
INSERT INTO learning_path_translation (
    id, title, description, created_at, language_id, learning_path_id
)
SELECT
    s,
    (ARRAY[
        'Complete Sales Professional Track',
        'Digital Marketing Career Path',
        'Finance for Business Leaders',
        'Executive Leadership Programme',
        'HR & People Management Track',
        'Operations Excellence Journey',
        'Strategic Management Path',
        'Entrepreneurship & Growth Track',
        'Customer Success Career Path',
        'Commercial Acumen Development Programme'
        ])[1 + (((s-1)/2) % 10)]
        || ' ' || (((s-1)/2) / 10 + 1),
    'A comprehensive learning journey designed to take you from foundational knowledge to professional mastery through curated expert-led courses, case studies, and practical assessments.',
    NOW() - ((s % 300) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 20000) s;

-- @ElementCollection: learning_path_translation_learning_outcomes
-- (4 per translation × 20 000 = 80 000 rows)
INSERT INTO learning_path_translation_learning_outcomes (
    learning_path_translation_id, learning_outcomes
)
SELECT
    ((s - 1) / 4) + 1,
    (ARRAY[
        'Build a complete professional skill set from foundations to advanced practice',
        'Apply proven frameworks and tools used by leading organisations worldwide',
        'Earn a professional certificate recognised by employers in your sector',
        'Lead teams, projects, and strategic initiatives with clarity and confidence',
        'Make evidence-based commercial decisions that improve business performance',
        'Communicate persuasively with stakeholders at every level of the organisation',
        'Analyse business challenges and design effective, practical solutions',
        'Advance your career with skills aligned to current employer demand'
        ])[1 + (s % 8)]
FROM generate_series(1, 80000) s;

-- curated_learning_path  (5 000 rows)
INSERT INTO curated_learning_path (id, learning_path_id)
SELECT s, s * 2
FROM generate_series(1, 5000) s;

-- expert_curated_learning_path join  (15 000 rows)
INSERT INTO expert_curated_learning_path (expert_id, curated_learning_path_id)
SELECT DISTINCT
    10 + (s % 1000) + 1,
    1 + (s % 5000)
FROM generate_series(1, 15000) s
ON CONFLICT DO NOTHING;

-- personalized_learning_path  (100 000 rows)
INSERT INTO personalized_learning_path (
    id, type, generated_reason, discounted,
    added_discount_percent, added_discount_amount,
    active, created_at, expires_at,
    learning_path_id, source_learning_path_id, user_id
)
SELECT
    s,
    (ARRAY['SYSTEM_RECOMMENDATION','ADJUSTED_LEARNING_PATH'])[1 + (s % 2)],
    (ARRAY['SKILL_GAP_DETECTED','CAREER_ADVANCEMENT',
        'ROLE_RELEVANT_SKILL','COURSE_ALREADY_ENROLLED'])[1 + (s % 4)],
    (s % 4 = 0),
    CASE WHEN s % 4 = 0 THEN (5 + (s % 20))::DECIMAL ELSE 0 END,
    CASE WHEN s % 4 = 0 THEN (15 + (s % 35))::DECIMAL ELSE 0 END,
    (s % 10) < 8,
    NOW() - ((s % 180) || ' days')::INTERVAL,
    NOW() + ((30 + s % 335) || ' days')::INTERVAL,
    1 + (s % 10000),
    CASE WHEN s % 2 = 0 THEN 1 + (s % 5000) ELSE NULL END,
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 100000) s;

-- learning_path_course  (60 000 rows – 6 courses per path)
INSERT INTO learning_path_course (id, sequence_order, learning_path_id, course_id)
SELECT
    s,
    1 + ((s - 1) % 6),
    (s - 1) / 6 + 1,
    1 + (s % 100000)
FROM generate_series(1, 60000) s;

-- user_learning_path  (300 000 rows)
INSERT INTO user_learning_path (
    id, acquired_date, completed_date, status,
    progress_percentage, updated_at, learning_path_id, user_id
)
SELECT
    s,
    CURRENT_DATE - ((s % 500) || ' days')::INTERVAL,
    CASE WHEN s % 5 = 0
             THEN CURRENT_DATE - ((s % 100) || ' days')::INTERVAL ELSE NULL END,
    (ARRAY['NOT_STARTED','IN_PROGRESS','IN_PROGRESS','IN_PROGRESS','COMPLETED'])[1 + (s % 5)],
    CASE WHEN s % 5 = 0 THEN 100 WHEN s % 5 = 1 THEN 0 ELSE 10 + (s % 90) END,
    NOW() - ((s % 30) || ' days')::INTERVAL,
    1 + (s % 10000),
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 300000) s;

-- =============================================================
-- COLLECTION: bundle  (5 000 rows)
-- =============================================================
INSERT INTO bundle (
    id, type, slug, image_url, base_price,
    discount_amount, discount_percentage, active, created_at, updated_at
)
SELECT
    s,
    (ARRAY['SYSTEM_GENERATED','CURATED','CURATED','PERSONALIZED'])[1 + (s % 4)],
    'bundle-' || s,
    'https://cdn.shifter.com/bundles/' || s || '/cover.jpg',
    (79 + (s % 220))::DECIMAL,
    CASE WHEN s % 3 = 0 THEN (10 + (s % 40))::DECIMAL ELSE 0 END,
    CASE WHEN s % 3 = 0 THEN (10 + (s % 30))::DECIMAL ELSE 0 END,
    (s % 15) < 13,
    NOW() - ((s % 730) || ' days')::INTERVAL,
    NOW() - ((s % 30)  || ' days')::INTERVAL
FROM generate_series(1, 5000) s;

-- bundle_translation  (10 000 rows – 2 per bundle)
INSERT INTO bundle_translation (id, title, description, created_at, language_id, bundle_id)
SELECT
    s,
    (ARRAY[
        'Sales & Negotiation Bundle',
        'Marketing & Brand Essentials Pack',
        'Finance & Accounting Bundle',
        'Leadership & Management Pack',
        'HR & People Strategy Bundle',
        'Operations & Supply Chain Pack',
        'Strategy & Innovation Bundle',
        'Entrepreneurship Starter Kit',
        'Customer Success & Service Pack',
        'Commercial Skills Accelerator Bundle'
        ])[1 + (((s-1)/2) % 10)]
        || ' Vol.' || (((s-1)/2) / 10 + 1),
    'A carefully curated bundle of expert-led courses that equip you with the core skills to excel in this discipline.',
    NOW() - ((s % 300) || ' days')::INTERVAL,
    CASE WHEN s % 2 = 1 THEN 1 ELSE 2 END,
    (s + 1) / 2
FROM generate_series(1, 10000) s;

-- curated_bundle  (2 500 rows)
INSERT INTO curated_bundle (id, bundle_id)
SELECT s, s * 2
FROM generate_series(1, 2500) s;

-- expert_curated_bundle join  (7 500 rows)
INSERT INTO expert_curated_bundle (expert_id, curated_bundle_id)
SELECT DISTINCT
    10 + (s % 1000) + 1,
    1 + (s % 2500)
FROM generate_series(1, 7500) s
ON CONFLICT DO NOTHING;

-- bundle_course  (25 000 rows – 5 per bundle)
INSERT INTO bundle_course (bundle_id, course_id)
SELECT
    1 + ((s - 1) / 5),
    1 + (s % 100000)
FROM generate_series(1, 25000) s
ON CONFLICT DO NOTHING;

-- user_bundle  (200 000 rows)
INSERT INTO user_bundle (id, acquired_date, bundle_id, user_id)
SELECT
    s,
    CURRENT_DATE - ((s % 500) || ' days')::INTERVAL,
    1 + (s % 5000),
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 200000) s;

-- personalized_bundle  (50 000 rows)
INSERT INTO personalized_bundle (
    id, type, generated_reason, discounted,
    added_discount_percent, added_discount_amount,
    active, created_at, expires_at,
    bundle_id, source_bundle_id, user_id
)
SELECT
    s,
    (ARRAY['SYSTEM_RECOMMENDATION','ADJUSTED_BUNDLE'])[1 + (s % 2)],
    (ARRAY['SKILL_GAP_DETECTED','CAREER_ADVANCEMENT',
        'ROLE_RELEVANT_SKILL','COURSE_ALREADY_ENROLLED'])[1 + (s % 4)],
    (s % 4 = 0),
    CASE WHEN s % 4 = 0 THEN (5 + (s % 15))::DECIMAL ELSE 0 END,
    CASE WHEN s % 4 = 0 THEN (10 + (s % 30))::DECIMAL ELSE 0 END,
    (s % 10) < 8,
    NOW() - ((s % 180) || ' days')::INTERVAL,
    NOW() + ((30 + s % 335) || ' days')::INTERVAL,
    1 + (s % 5000),
    CASE WHEN s % 2 = 0 THEN 1 + (s % 2500) ELSE NULL END,
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 50000) s;

-- =============================================================
-- IDENTITY: user join tables
-- =============================================================

-- user_favorite_course  (500 000 rows)
INSERT INTO user_favorite_course (user_id, course_id)
SELECT DISTINCT
    1010 + 1 + (s % 2000000),
    1 + (s % 100000)
FROM generate_series(1, 500000) s
ON CONFLICT DO NOTHING;

-- user_topic  (400 000 rows)
INSERT INTO user_topic (user_id, topic_id)
SELECT DISTINCT
    1010 + 1 + (s % 2000000),
    1 + (s % 500)
FROM generate_series(1, 400000) s
ON CONFLICT DO NOTHING;

-- =============================================================
-- CATALOG: course_activity_event  (15 000 000 rows)
-- =============================================================
INSERT INTO course_activity_event (id, event_type, timestamp, course_id, user_id)
SELECT
    s,
    (ARRAY[
        'COURSE_VIEWED','COURSE_ENROLLED','COURSE_STARTED',
        'LESSON_STARTED','LESSON_COMPLETED','MODULE_COMPLETED',
        'VIDEO_WATCHED','QUIZ_ATTEMPTED','QUIZ_PASSED','QUIZ_FAILED',
        'COURSE_COMPLETED','COURSE_WISHLISTED','CERTIFICATE_DOWNLOADED',
        'COURSE_REVIEWED','COURSE_SHARED','RESOURCE_DOWNLOADED',
        'EXERCISE_SUBMITTED','COURSE_ABANDONED'
        ])[1 + (s % 18)],
    NOW() - ((s % 1095) || ' days')::INTERVAL,
    1 + (s % 100000),
    1010 + 1 + (s % 2000000)
FROM generate_series(1, 15000000) s;

-- =============================================================
-- SEQUENCE RESETS
-- =============================================================
SELECT setval('language_seq',                       10,        true);
SELECT setval('admin_seq',                          20,        true);
SELECT setval('expert_seq',                         1100,      true);
SELECT setval('user_seq',                          2100000,   true);
SELECT setval('topic_seq',                          600,       true);
SELECT setval('topic_translation_seq',              1100,      true);
SELECT setval('skill_seq',                          1100,      true);
SELECT setval('skill_translation_seq',              2100,      true);
SELECT setval('course_seq',                         110000,    true);
SELECT setval('course_version_seq',                 210000,    true);
SELECT setval('course_price_seq',                   110000,    true);
SELECT setval('course_translation_seq',             210000,    true);
SELECT setval('course_module_seq',                  1100000,   true);
SELECT setval('course_module_translation_seq',      2100000,   true);
SELECT setval('course_lecture_seq',                 4100000,   true);
SELECT setval('course_lecture_translation_seq',     8100000,   true);
SELECT setval('related_course_seq',                 210000,    true);
SELECT setval('quiz_seq',                           110000,    true);
SELECT setval('quiz_translation_seq',               210000,    true);
SELECT setval('quiz_question_seq',                  1100000,   true);
SELECT setval('quiz_question_translation_seq',      2100000,   true);
SELECT setval('quiz_answer_option_seq',             4100000,   true);
SELECT setval('quiz_answer_option_translation_seq', 8100000,   true);
SELECT setval('quiz_question_skill_seq',            1100000,   true);
SELECT setval('quiz_attempt_seq',                   3100000,   true);
SELECT setval('quiz_attempt_answer_seq',            6100000,   true);
SELECT setval('enrollment_seq',                     10100000,  true);
SELECT setval('lecture_progress_seq',               12100000,  true);
SELECT setval('user_skill_seq',                     2100000,   true);
SELECT setval('user_skill_snapshot_seq',            1100000,   true);
SELECT setval('review_seq',                         600000,    true);
SELECT setval('certificate_seq',                    310000,    true);
SELECT setval('order_seq',                         1100000,   true);
SELECT setval('payment_seq',                        1100000,   true);
SELECT setval('order_details_seq',                  1100000,   true);
SELECT setval('meeting_email_reminder_seq',         210000,    true);
SELECT setval('learning_path_seq',                  11000,     true);
SELECT setval('learning_path_translation_seq',      21000,     true);
SELECT setval('curated_learning_path_seq',          6000,      true);
SELECT setval('personalized_learning_path_seq',     110000,    true);
SELECT setval('learning_path_course_seq',           70000,     true);
SELECT setval('user_learning_path_seq',             310000,    true);
SELECT setval('bundle_seq',                         6000,      true);
SELECT setval('bundle_translation_seq',             11000,     true);
SELECT setval('curated_bundle_seq',                 3000,      true);
SELECT setval('personalized_bundle_seq',            60000,     true);
SELECT setval('user_bundle_seq',                    210000,    true);
SELECT setval('course_activity_event_seq',          15100000,  true);

-- COMMIT;
