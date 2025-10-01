
-- Foundations for a Successful Business & Career
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Understanding Paradigms & Mental Frameworks', 1, 1),
    ('Exploring Perception and Perspective', 2, 1),
    ('Transforming Mindsets for Growth', 3, 1);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Understanding Paradigms & Mental Frameworks (course_content_id: 1)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will gain a deep understanding of paradigms and their role in shaping mental frameworks that drive daily decision-making. This video covers how paradigms form, their impact on success, and strategies to identify limiting beliefs. You will learn to recognize your own paradigms and leverage them for professional growth. Action: Watch the video and list three paradigms influencing your decisions.',
     20, 1, 'Introduction to Paradigms and Mental Frameworks for Success', 1),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s.',
     'TEXT', NULL,
     'Users will discover how paradigms shape decision-making in business and personal contexts. This article covers common paradigm types, their effects on behavior, and methods to reframe limiting paradigms. You will gain insights into adapting mental models for better outcomes. Action: Reflect on your mental models and write a summary of one paradigm you wish to change.',
     15, 2, 'Understanding How Paradigms Influence Decision-Making', 1),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will acquire tools to identify and assess their current paradigms through this worksheet. It provides exercises to pinpoint paradigms that help or hinder progress and offers strategies to reframe them. You will learn to create actionable plans for paradigm shifts. Action: Complete the worksheet and discuss findings with a peer before the next session.',
     18, 3, 'Worksheet for Identifying and Reframing Paradigms', 1),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their understanding of paradigms and mental frameworks through this interactive quiz. It covers key concepts like paradigm formation and reframing techniques, helping you assess your grasp of these ideas. You will gain clarity on areas for improvement. Action: Complete the quiz and review incorrect answers to enhance your knowledge.',
     15, 4, 'Interactive Quiz on Paradigms and Mental Frameworks', 1),

    -- Exploring Perception and Perspective (course_content_id: 2)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn the critical differences between perception and perspective in leadership and business settings. This video covers their influence on decision-making, team dynamics, and strategic planning, with real-world examples. You will gain skills to apply these concepts effectively. Action: Watch the video and note two examples of how perception or perspective shapes your decisions.',
     22, 1, 'Exploring Perception and Perspective in Leadership and Business', 2),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will understand how perception and perspective shape professional interactions and outcomes. This article covers their psychological and practical aspects, conflict resolution techniques, and perspective-shifting strategies. You will gain tools to improve collaboration and results. Action: Reflect on a recent interaction and identify how your perspective influenced the outcome.',
     14, 2, 'Deep Dive into Perception and Perspective in Professional Interactions', 2),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will identify gaps in their perception and perspective using this worksheet. It provides exercises to realign perspectives with professional goals and foster collaboration. You will learn to create actionable realignment strategies. Action: Complete the worksheet and discuss findings in a group setting.',
     16, 3, 'Worksheet for Realigning Perspectives with Professional Goals', 2),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will assess their knowledge of perception and perspective concepts through this quiz. It covers distinctions, applications, and common pitfalls, providing feedback on your understanding. You will gain insights into areas for improvement. Action: Complete the quiz and review results to refine your approach.',
     18, 4, 'Interactive Quiz on Perception and Perspective Concepts', 2),

    -- Transforming Mindsets for Growth (course_content_id: 3)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will acquire practical steps to transform their mindset for growth and adaptability in professional settings. This video covers reframing challenges, embracing feedback, and cultivating resilience, with case studies. You will gain actionable strategies for a growth mindset. Action: Watch the video and practice one mindset transformation technique this week.',
     25, 1, 'Practical Steps for Transforming Mindsets to Achieve Growth', 3),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will learn how to shift mental models for success in business and career through this guide. It covers adopting a growth mindset, overcoming fixed mindset barriers, and integrating new habits. You will gain insights into sustainable mindset changes. Action: Implement one practice and track its impact for one week.',
     16, 2, 'Comprehensive Guide to Shifting Mental Models for Success', 3),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will track mindset changes and progress toward a growth-oriented approach using this workbook. It provides templates for goal-setting, reflecting on challenges, and evaluating outcomes. You will learn to maintain consistent progress. Action: Update the workbook regularly and review progress monthly.',
     20, 3, 'Workbook for Tracking Mindset Changes and Progress', 3),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their understanding of mindset transformation techniques through this quiz. It covers practical implementation and key concepts, helping you identify growth areas. You will gain feedback to solidify your learning. Action: Complete the quiz and review answers to enhance your knowledge.',
     22, 4, 'Final Quiz on Mindset Transformation Techniques', 3);

-- From Manager to Leader
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Leadership Mindsets & Models', 1, 2),
    ('Communication & Delegation Mastery', 2, 2),
    ('Building Accountability & Trust', 3, 2),
    ('Navigating Organizational Change', 4, 2);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Leadership Mindsets & Models (course_content_id: 4)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will gain insights into leadership mindsets, including the 3xH’s framework (Head, Heart, Hands), and their role in effective leadership. This video covers how these mindsets differentiate managers from leaders, with real-world examples. You will learn to cultivate impactful leadership traits. Action: Watch the video and identify one 3xH trait to focus on this week.',
     23, 1, 'Introduction to Leadership Mindsets and the 3xH Framework', 4),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will understand generative leadership and models like AB and SMART objectives through this article. It covers their applications in strategic decision-making and team success. You will gain tools to apply leadership frameworks effectively. Action: Study the article and apply one model to a current project.',
     18, 2, 'Exploring Generative Leadership and Key Models for Success', 4),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will assess their leadership strengths and development areas using this worksheet. It provides exercises to evaluate alignment with leadership models and identify growth opportunities. You will learn to create targeted development plans. Action: Complete the worksheet and reflect on your results.',
     15, 3, 'Self-Assessment Worksheet for Leadership Strengths', 4),

    -- Communication & Delegation Mastery (course_content_id: 5)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will master communication strategies and delegation techniques for leadership through this video. It covers assigning tasks clearly, motivating teams, and avoiding delegation pitfalls, with examples. You will gain skills to enhance leadership through communication. Action: Watch the video and practice one delegation scenario with your team.',
     24, 1, 'Mastering Communication and Delegation Techniques for Leaders', 5),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will learn to balance responsibility and guilt in leadership communication through this article. It covers active listening, feedback delivery, and empathetic interaction strategies. You will gain tools to refine your communication style. Action: Reflect on your communication style and practice one technique this week.',
     14, 2, 'Balancing Responsibility in Leadership Communication', 5),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will streamline task assignment with this delegation checklist and templates. It provides tools to enhance team efficiency and accountability through structured processes. You will learn to implement effective delegation strategies. Action: Use the templates to delegate a task and review the outcome.',
     12, 3, 'Delegation Checklists and Templates for Effective Task Management', 5),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their knowledge of leadership communication and delegation principles through this quiz. It covers strategies, challenges, and best practices, providing feedback on your skills. You will gain insights into areas for improvement. Action: Complete the quiz and review results to enhance your approach.',
     18, 4, 'Interactive Quiz on Leadership Communication and Delegation', 5),

    -- Building Accountability & Trust (course_content_id: 6)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to build accountability and trust within teams through this video. It covers transparent communication, consistent follow-through, and real-world examples of trust-building strategies. You will gain actionable steps to foster a reliable team culture. Action: Watch the video and implement one trust-building strategy with your team.',
     22, 1, 'Strategies for Building Accountability and Trust in Teams', 6),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will understand the difference between responsibility and guilt in fostering accountability through this article. It provides frameworks for setting expectations and maintaining trust. You will gain tools to create a supportive team environment. Action: Read the article and discuss one framework with your team.',
     16, 2, 'Fostering Accountability and Trust in Team Dynamics', 6),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will build accountability within teams using this worksheet. It provides templates for setting expectations and tracking progress, helping you strengthen team trust. You will learn to implement accountability systems. Action: Fill out the worksheet with your team and review progress.',
     15, 3, 'Worksheet for Building Accountability Frameworks in Teams', 6),

    -- Navigating Organizational Change (course_content_id: 7)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn strategies for navigating organizational change and breaking silos through this video. It covers aligning teams, managing resistance, and fostering collaboration, with case studies. You will gain skills to lead change initiatives successfully. Action: Watch the video and prepare one change management strategy for your organization.',
     28, 1, 'Navigating Organizational Change and Breaking Silos', 7),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will discover steps to engage middle management and foster collaboration during organizational change. This article covers frameworks for managing transitions and aligning teams with new goals. You will gain tools to implement effective change strategies. Action: Apply one step to a current change initiative.',
     20, 2, 'Engaging Teams for Effective Organizational Change Management', 7),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will assess their knowledge of change management and organizational dynamics through this quiz. It covers collaboration strategies and resistance management, providing feedback on your understanding. You will gain insights into areas for improvement. Action: Complete the quiz and review results to refine your approach.',
     17, 3, 'Interactive Quiz on Change Management and Organizational Dynamics', 7);

-- Business Transformation Blueprint
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Foundations of Business Transformation', 1, 3),
    ('Engaging Stakeholders & Middle Management', 2, 3),
    ('Implementing Change & Measuring Success', 3, 3),
    ('Building Agile & Resilient Organizations', 4, 3);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Foundations of Business Transformation (course_content_id: 8)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will gain a comprehensive understanding of business transformation principles and stages through this video. It covers transformation triggers like market shifts and internal inefficiencies, with a roadmap for success. You will learn to identify transformation opportunities in your business. Action: Watch the video and note three relevant transformation triggers.',
     26, 1, 'Introduction to Principles and Stages of Business Transformation', 8),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will learn about transformation drivers and setting a clear purpose for change initiatives. This article covers strategic planning, stakeholder alignment, and pitfalls to avoid. You will gain insights into launching effective transformation projects. Action: Reflect on your organization’s transformation readiness.',
     18, 2, 'Understanding Drivers and Purpose in Business Transformation', 8),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will assess their business’s readiness for transformation using this template. It provides exercises to evaluate strengths, weaknesses, and external factors. You will learn to create a transformation readiness plan. Action: Complete the template with your team and discuss results.',
     15, 3, 'Template for Assessing Business Transformation Readiness', 8),

    -- Engaging Stakeholders & Middle Management (course_content_id: 9)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn strategies to engage middle management as change agents in transformation initiatives. This video covers communication techniques, stakeholder mapping, and gaining buy-in, with examples. You will gain skills to align stakeholders effectively. Action: Plan a stakeholder communication strategy for your organization.',
     23, 1, 'Engaging Middle Management as Change Agents in Transformation', 9),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will discover best practices for stakeholder alignment and communication during transformation. This article covers identifying stakeholders, addressing concerns, and fostering collaboration. You will gain tools to build strong stakeholder relationships. Action: Analyze your stakeholder map and identify one improvement area.',
     17, 2, 'Best Practices for Stakeholder Alignment and Communication', 9),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will structure stakeholder engagement with this communication plan template. It provides tools to outline messaging, timelines, and feedback mechanisms. You will learn to create effective communication strategies. Action: Customize the template for your change initiative.',
     13, 3, 'Communication Plan Template for Change Initiatives', 9),

    -- Implementing Change & Measuring Success (course_content_id: 10)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to implement KPIs and monitor transformation progress through this video. It covers selecting relevant KPIs, tracking tools, and interpreting data. You will gain skills to measure transformation effectively. Action: Apply one KPI measurement tool to your initiative.',
     27, 1, 'Implementing KPIs and Monitoring Transformation Progress', 10),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will understand how to define and use KPIs effectively to track transformation. This article covers selecting measurable metrics, aligning with goals, and avoiding pitfalls. You will gain a robust KPI framework. Action: Set three KPIs for your transformation project and outline their tracking process.',
     19, 2, 'Guide to Defining and Using KPIs in Business Transformation', 10),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will monitor transformation progress with this KPI tracking dashboard template. It provides tools to visualize data and evaluate outcomes. You will learn to implement and maintain KPI tracking systems. Action: Implement the dashboard and monitor KPIs weekly.',
     14, 3, 'KPI Tracking Dashboard Template for Transformation Monitoring', 10),

    -- Building Agile & Resilient Organizations (course_content_id: 11)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to build agility and resilience into organizational structures through this video. It covers flexible frameworks, adaptive strategies, and examples of resilient organizations. You will gain skills to design agile systems. Action: Assess your organization’s structure for agility.',
     29, 1, 'Building Agility and Resilience in Organizational Structures', 11),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will understand concepts and frameworks for creating agile organizations. This article covers organizational design, cross-functional collaboration, and resilience strategies. You will gain tools to improve your structure. Action: Evaluate your organizational design and identify one agility improvement.',
     20, 2, 'Frameworks for Creating Agile and Resilient Organizations', 11),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will assess their understanding of agile and resilient structures through this quiz. It covers flexibility and adaptability strategies, providing feedback on your knowledge. You will gain insights into areas for improvement. Action: Complete the quiz and review results to enhance your understanding.',
     18, 3, 'Interactive Quiz on Agile and Resilient Organizational Structures', 11);

-- SME Business Roadmap
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Core Foundations for SME Success', 1, 4),
    ('Aligning Teams & Vision', 2, 4),
    ('Building Sustainable Growth Systems', 3, 4);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Core Foundations for SME Success (course_content_id: 12)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will discover core foundations for SME growth and stability through this video. It covers strategic planning, resource management, and examples of successful SMEs. You will gain skills to build a strong business base. Action: Note three key foundations for your SME.',
     24, 1, 'Core Foundations for Driving SME Growth and Stability', 12),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will learn to overcome common SME challenges like financial management and market positioning. This article covers practical strategies to strengthen your business foundation. You will gain insights into addressing SME-specific obstacles. Action: Write a plan to address one business challenge.',
     15, 2, 'Overcoming Challenges for SME Success and Growth', 12),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will assess their SME’s foundational strengths and weaknesses with this worksheet. It provides exercises to evaluate financial, operational, and strategic readiness. You will learn to create a robust foundation plan. Action: Complete the worksheet and discuss with your team.',
     17, 3, 'Assessment Worksheet for SME Foundational Strengths', 12),

    -- Aligning Teams & Vision (course_content_id: 13)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to align their SME team around a shared vision and mission through this video. It covers fostering team buy-in, creating a cohesive culture, and real-world examples. You will gain skills to unify your team. Action: Implement one alignment activity with your team.',
     22, 1, 'Aligning SME Teams Around a Shared Vision and Mission', 13),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will discover steps to build a motivating shared vision for their SME team. This article covers vision-setting workshops, communication strategies, and alignment techniques. You will gain tools to create a cohesive team vision. Action: Lead a vision-building session with your team.',
     14, 2, 'Steps to Build a Motivating Vision for SME Teams', 13),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will craft a clear and inspiring vision for their SME using this template. It provides tools to articulate values and goals that resonate with your team. You will learn to formalize your vision. Action: Customize the template for your SME’s vision and mission.',
     13, 3, 'Vision and Mission Template for SME Alignment', 13),

    -- Building Sustainable Growth Systems (course_content_id: 14)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to build scalable systems for sustainable SME growth through this video. It covers process optimization, automation, and scalability strategies, with examples. You will gain skills to design growth-oriented systems. Action: Plan one system implementation for your SME.',
     26, 1, 'Building Scalable Systems for Sustainable SME Growth', 14),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will understand operational efficiency and process design for SMEs through this article. It covers frameworks for streamlining workflows and ensuring scalability. You will gain tools to optimize business processes. Action: Map one SME process and identify an efficiency improvement.',
     18, 2, 'Operational Efficiency and Process Design for SMEs', 14),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will design growth-oriented processes for their SME with this systems planning worksheet. It provides templates for mapping workflows and identifying scalability factors. You will learn to create sustainable systems. Action: Use the worksheet to design one growth-oriented system.',
     16, 3, 'Systems Planning Worksheet for SME Growth', 14);

-- Sales Masterclass
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Sales Leadership Fundamentals', 1, 5),
    ('Advanced Sales Techniques', 2, 5),
    ('Building High-Performance Sales Teams', 3, 5),
    ('Negotiation & Client Relationships', 4, 5);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Sales Leadership Fundamentals (course_content_id: 15)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will gain insights into sales leadership principles and mindset through this video. It covers building team synergy, vision-setting, and motivation, with real-world examples. You will learn to lead sales teams effectively. Action: Reflect on your current sales leadership approach.',
     23, 1, 'Introduction to Sales Leadership Principles and Mindset', 15),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will understand the role of leadership in sales through this article. It covers building synergy and aligning teams with business goals, with strategies for motivation. You will gain tools to enhance your leadership impact. Action: Apply one strategy to improve your team’s influence.',
     17, 2, 'Building Synergy and Influence in Sales Leadership', 15),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will assess their leadership strengths and growth areas with this worksheet. It provides exercises to evaluate skills and set development goals for sales managers. You will learn to create a personal growth plan. Action: Complete the worksheet and set one leadership goal.',
     15, 3, 'Self-Assessment Worksheet for Sales Leadership Growth', 15),

    -- Advanced Sales Techniques (course_content_id: 16)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will master advanced sales techniques to increase close rates through this video. It covers consultative selling, objection handling, and practical demonstrations. You will gain skills to refine your sales approach. Action: Practice one technique in your next sales interaction.',
     27, 1, 'Advanced Sales Techniques for Increasing Close Rates', 16),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will learn to overcome sales silos and improve collaboration through this article. It covers pipeline management, customer engagement, and advanced sales strategies. You will gain tools to optimize your sales process. Action: Analyze your sales process and identify one improvement area.',
     16, 2, 'Overcoming Silos and Optimizing Sales Collaboration', 16),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their knowledge of advanced sales strategies through this quiz. It covers objection handling, pipeline management, and other key concepts, providing feedback on your skills. You will gain insights into areas for improvement. Action: Complete the quiz and review results to refine your approach.',
     20, 3, 'Interactive Quiz on Advanced Sales Strategies', 16),

    -- Building High-Performance Sales Teams (course_content_id: 17)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to build and manage high-performance sales teams through this video. It covers recruitment, training, and motivation strategies, with examples of successful teams. You will gain skills to design a team improvement plan. Action: Draft a plan to enhance your sales team’s performance.',
     28, 1, 'Building and Managing High-Performance Sales Teams', 17),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will discover steps to foster accountability and growth in sales teams. This article covers setting expectations, tracking performance, and building team cohesion. You will gain tools to create a high-performance culture. Action: Implement one accountability strategy with your sales team.',
     18, 2, 'Fostering Accountability and Growth in Sales Teams', 17),
    (NULL, 'FILE', 'https://drive.google.com/file/d/14LBFO5sNWu7P3L8Co-MeGUxMbg7fhC76/view?usp=drive_link',
     'Users will track sales team KPIs and goals with this template. It provides tools to set measurable targets and monitor progress, helping you maintain team accountability. You will learn to sustain team performance. Action: Fill out the template and track your team’s performance regularly.',
     15, 3, 'KPI Tracking Template for Sales Team Performance', 17),

    -- Negotiation & Client Relationships (course_content_id: 18)
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn strategies for effective negotiation and building strong client relationships through this video. It covers handling objections, building trust, and closing deals, with real-world scenarios. You will gain skills to strengthen client partnerships. Action: Prepare one negotiation scenario for practice.',
     25, 1, 'Strategies for Effective Negotiation and Client Relationships', 18),
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
     'TEXT', NULL,
     'Users will discover how to turn client objections into opportunities for stronger relationships. This article covers negotiation frameworks, active listening, and trust-building techniques. You will gain tools to enhance client interactions. Action: Apply one technique in your next client meeting.',
     19, 2, 'Turning Client Objections into Relationship Opportunities', 18),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will evaluate their negotiation and client management skills through this quiz. It covers objection handling and relationship-building strategies, providing feedback on your skills. You will gain insights into areas for improvement. Action: Complete the quiz and review results for enhancement.',
     17, 3, 'Interactive Quiz on Negotiation and Client Management Skills', 18);

-- Mastering Success Skills
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Foundations of Effective Skill-Building', 1, 6),
    ('Time Management & Prioritization Strategies', 2, 6),
    ('Communication & Cognitive Tools for Success', 3, 6),
    ('Transforming Good Habits into Extraordinary Results', 4, 6);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Foundations of Effective Skill-Building (course_content_id: 19)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will learn the core principles behind building essential skills for personal and professional growth. This article covers the psychology of skill acquisition, learning frameworks, and practical applications. You will gain a structured skill-building plan. Action: Reflect on your current skills and outline one skill to develop.',
     15, 1, 'Core Principles of Effective Skill-Building for Success', 19),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore practical frameworks for mastering new skills through this video. It covers goal-setting, feedback loops, and examples of successful skill-building strategies. You will gain a solid foundation for skill development. Action: List three key takeaways for your skill-building plan.',
     20, 2, 'Practical Frameworks for Mastering New Skills', 19),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their understanding of skill-building principles through this quiz. It covers learning frameworks and practical applications, helping you identify areas for improvement. You will gain feedback on your knowledge. Action: Complete the quiz and review results to refine your approach.',
     10, 3, 'Interactive Quiz on Skill-Building Principles', 19),

    -- Time Management & Prioritization Strategies (course_content_id: 20)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will discover proven methods to prioritize tasks and manage time effectively. This article covers techniques like the Eisenhower Matrix and time-blocking, with practical tips. You will gain tools to boost productivity without burnout. Action: Implement the weekly planner for one week.',
     18, 1, 'Proven Methods for Effective Time Management and Prioritization', 20),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn advanced time management techniques to enhance productivity and balance. This video covers prioritization frameworks and workload management tools. You will gain skills to optimize your schedule. Action: Apply one time management technique today.',
     25, 2, 'Advanced Techniques for Time Management and Productivity', 20),

    -- Communication & Cognitive Tools for Success (course_content_id: 21)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will learn how powerful communication and cognitive strategies drive success. This article covers active listening, emotional intelligence, and problem-solving tools. You will gain skills to enhance effectiveness in dynamic environments. Action: Practice active listening in your next team meeting.',
     20, 1, 'Powerful Communication and Cognitive Strategies for Success', 21),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore cognitive tools to adapt, grow, and thrive in dynamic settings. This video covers decision-making frameworks, communication techniques, and real-world applications. You will gain skills to apply cognitive strategies effectively. Action: Identify one insight to implement in your work.',
     28, 2, 'Cognitive Tools and Communication Techniques for Growth', 21),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will assess their communication maturity and readiness for high-stakes situations. This quiz covers active listening and cognitive strategies, providing feedback on your skills. You will gain insights into areas for improvement. Action: Complete the quiz and reflect on results to improve.',
     12, 3, 'Interactive Quiz on Communication and Cognitive Skills', 21),

    -- Transforming Good Habits into Extraordinary Results (course_content_id: 22)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will learn to transform everyday habits into extraordinary results. This article covers habit formation, consistency frameworks, and sustained improvement strategies. You will gain tools to build high-impact habits. Action: Write a new habit plan based on the article.',
     22, 1, 'Transforming Everyday Habits into High-Impact Results', 22),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore how successful individuals design habits for continuous improvement. This video covers case studies and practical habit-building strategies. You will gain skills to create sustainable habits. Action: Implement one habit-building strategy today.',
     27, 2, 'Case Studies on Designing Habits for Continuous Improvement', 22);

-- Winning Markets in 3 Steps
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Defining Your Ideal Client Profile', 1, 7),
    ('Crafting Precise Messaging and Value Propositions', 2, 7),
    ('Choosing Effective Marketing Channels', 3, 7),
    ('Integrating Strategy into Execution', 4, 7);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Defining Your Ideal Client Profile (course_content_id: 23)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will learn to define an ideal client profile to focus marketing efforts. This article covers analyzing customer needs, behaviors, and demographics for targeted strategies. You will gain skills to create a precise client profile. Action: Write your ideal client profile.',
     18, 1, 'Defining the Eight Characteristics of an Ideal Client Profile', 23),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore real-world examples of businesses defining ideal client profiles. This video covers strategies for identifying high-value clients and tailoring offerings. You will gain skills to refine client targeting. Action: Note one applicable insight for your business.',
     24, 2, 'Real-World Examples of Defining Ideal Client Profiles', 23),

    -- Crafting Precise Messaging and Value Propositions (course_content_id: 24)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will learn to create messaging that resonates with their audience. This article covers frameworks for crafting compelling value propositions and aligning with client needs. You will gain skills to develop impactful messaging. Action: Draft your core message.',
     20, 1, 'Crafting Resonant Messaging and Value Propositions', 24),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will discover techniques for building powerful value propositions that stand out. This video covers successful messaging strategies and their impact, with examples. You will gain skills to create compelling value propositions. Action: Identify your main value points.',
     26, 2, 'Techniques for Building Powerful Value Propositions', 24),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their understanding of messaging and value propositions through this quiz. It covers messaging frameworks and key concepts, providing feedback on your skills. You will gain insights into areas for improvement. Action: Complete the quiz and adjust your messaging.',
     12, 3, 'Interactive Quiz on Messaging and Value Propositions', 24),

    -- Choosing Effective Marketing Channels (course_content_id: 25)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will learn to identify where their audience is most active through this article. It covers analyzing digital, traditional, and hybrid marketing channels, with selection strategies. You will gain skills to choose effective channels. Action: Choose your top three marketing channels.',
     17, 1, 'Analyzing and Selecting Effective Marketing Channels', 25),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore how to balance multiple marketing channels with consistent messaging. This video covers channel integration, audience targeting, and performance tracking. You will gain skills to create a cohesive channel strategy. Action: Create your channel strategy.',
     25, 2, 'Balancing Multiple Channels for Consistent Marketing', 25),

    -- Integrating Strategy into Execution (course_content_id: 26)
    ('Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text...',
     'TEXT', NULL,
     'Users will learn to combine client profiles, messaging, and channels into a go-to-market strategy. This article covers strategic planning, execution steps, and alignment techniques. You will gain skills to build an effective roadmap. Action: Build your go-to-market roadmap.',
     22, 1, 'Integrating Client Profiles and Messaging into a Go-to-Market Strategy', 26),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will discover how top companies align go-to-market plans for maximum impact. This video covers case studies and practical execution steps. You will gain skills to implement cohesive strategies. Action: List three strategic takeaways for your business.',
     28, 2, 'Case Studies on Aligning Go-to-Market Strategies for Impact', 26),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will check their readiness to launch an integrated go-to-market strategy through this quiz. It covers strategy alignment and execution, providing feedback on your plans. You will gain insights into gaps to address. Action: Complete the quiz and revise your strategy.',
     14, 3, 'Interactive Quiz on Integrated Go-to-Market Strategies', 26);

-- How to Create Added Value
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Understanding Client Problems Deeply', 1, 8),
    ('Designing Impactful Solutions', 2, 8),
    ('Developing Scalable Concepts', 3, 8),
    ('Positioning Yourself as a Strategic Partner', 4, 8);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Understanding Client Problems Deeply (course_content_id: 27)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to uncover hidden client problems and analyze root causes. This article covers needs analysis, stakeholder interviews, and practical applications. You will gain skills to identify high-impact client pain points. Action: List your top three client pain points.',
     20, 1, 'Uncovering and Analyzing Hidden Client Problems', 27),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore case studies where deep problem analysis led to breakthrough solutions. This video covers methodologies for identifying client needs and prioritizing solutions. You will gain skills to conduct effective problem discovery. Action: Note one methodology to apply to your clients.',
     25, 2, 'Case Studies on Effective Client Problem Discovery', 27),

    -- Designing Impactful Solutions (course_content_id: 28)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to design solutions addressing core client problems. This article covers design thinking, iterative development, and solution validation techniques. You will gain skills to create impactful solutions. Action: Sketch a solution concept for one client problem.',
     22, 1, 'Designing Solutions for Core Client Problems', 28),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will discover iterative design approaches for refining solutions. This video covers successful solution development and testing processes, with examples. You will gain skills to iterate effectively. Action: Identify one area to test in your solution design.',
     28, 2, 'Iterative Approaches to Solution Design and Refinement', 28),

    -- Developing Scalable Concepts (course_content_id: 29)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to build concepts that scale across client needs. This article covers scalability factors, modular design, and cross-industry applications. You will gain skills to create versatile solutions. Action: Define scalability factors for one concept.',
     19, 1, 'Building Scalable Concepts for Diverse Client Needs', 29),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore how top brands create scalable solutions and concepts. This video covers case studies and strategies for scalability and adaptability. You will gain skills to design scalable concepts. Action: Draft a scalability plan for one concept.',
     26, 2, 'Case Studies on Creating Scalable Solutions and Concepts', 29),

    -- Positioning Yourself as a Strategic Partner (course_content_id: 30)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to position themselves as strategic partners, not just vendors. This article covers relationship-building, value alignment, and trust strategies. You will gain skills to foster long-term client loyalty. Action: Write your value positioning statement.',
     21, 1, 'Positioning as a Strategic Partner for Client Loyalty', 30),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will discover relationship-based strategies for long-term client loyalty. This video covers examples of strategic partnerships and practical steps. You will gain skills to build strong client relationships. Action: Map your partner strategy.',
     27, 2, 'Relationship-Based Strategies for Long-Term Client Loyalty', 30),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their knowledge of moving from services to strategic partnerships. This quiz covers relationship-building techniques and key concepts, providing feedback on your understanding. You will gain insights into areas for improvement. Action: Complete the quiz and review results to refine your approach.',
     15, 3, 'Interactive Quiz on Strategic Partnership and Value Creation', 30);

-- Seamless Onboarding Strategy
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Pre-Onboarding Preparation', 1, 9),
    ('First Week Essentials', 2, 9),
    ('Long-Term Integration and Development', 3, 9);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Pre-Onboarding Preparation (course_content_id: 31)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to prepare for a new hire’s arrival to ensure smooth onboarding. This article covers creating pre-onboarding checklists, setting expectations, and aligning resources. You will gain skills to set new hires up for success. Action: Create a pre-onboarding checklist.',
     18, 1, 'Preparing for a Smooth New Hire Onboarding Process', 31),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore how pre-onboarding impacts early employee engagement. This video covers case studies of effective pre-onboarding strategies and their outcomes. You will gain skills to enhance onboarding readiness. Action: Identify one improvement for your pre-onboarding process.',
     22, 2, 'Case Studies on Effective Pre-Onboarding Strategies', 31),

    -- First Week Essentials (course_content_id: 32)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to plan structured activities for a new hire’s first week to build connection. This article covers team introductions, role clarity, and initial goal-setting. You will gain skills to design an effective first week. Action: Outline your first-week onboarding agenda.',
     19, 1, 'Designing a Connected First Week for New Hires', 32),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore successful approaches to immersive onboarding weeks. This video covers schedules, team-building activities, and orientation strategies. You will gain skills to create impactful first weeks. Action: Draft your week 1 onboarding schedule.',
     25, 2, 'Creating an Immersive First Week for New Hires', 32),

    -- Long-Term Integration and Development (course_content_id: 33)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to extend onboarding for continuous growth and integration. This article covers 90-day plans, mentorship programs, and feedback loops. You will gain skills to sustain onboarding success. Action: Design a 90-day integration plan.',
     20, 1, 'Extending Onboarding for Continuous Employee Development', 33),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to reinforce learning and build long-term retention strategies during onboarding. This video covers follow-up processes and development frameworks, with examples. You will gain skills to sustain employee engagement. Action: Create a follow-up process.',
     24, 2, 'Sustaining Long-Term Engagement in Onboarding', 33),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will test their understanding of seamless onboarding journeys through this quiz. It covers pre-onboarding and long-term strategies, providing feedback on your knowledge. You will gain insights into areas for improvement. Action: Complete the quiz and review results to improve.',
     13, 3, 'Interactive Quiz on Seamless Onboarding Strategies', 33);

-- Growth Through Self-Assessment
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Identifying Personal Barriers', 1, 10),
    ('Developing Actionable Improvement Plans', 2, 10),
    ('Implementing Continuous Self-Improvement', 3, 10);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Identifying Personal Barriers (course_content_id: 34)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to identify hidden barriers hindering professional growth. This article covers common blockers like self-doubt and procrastination, with self-assessment strategies. You will gain skills to pinpoint growth obstacles. Action: List your top three personal blockers.',
     20, 1, 'Identifying Hidden Barriers to Professional Growth', 34),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore examples of professionals overcoming personal barriers. This video covers self-reflection and barrier identification techniques, with practical applications. You will gain skills to address your blockers. Action: Reflect on one barrier and plan to address it.',
     26, 2, 'Real-World Examples of Overcoming Personal Barriers', 34),

    -- Developing Actionable Improvement Plans (course_content_id: 35)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to translate self-reflection into actionable improvement plans. This article covers goal-setting frameworks, prioritization techniques, and progress tracking. You will gain skills to create effective growth plans. Action: Draft your improvement roadmap.',
     22, 1, 'Creating Actionable Plans for Professional Improvement', 35),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore frameworks for goal setting and progress tracking in self-improvement. This video covers successful action plans and their outcomes, with examples. You will gain skills to design actionable plans. Action: Outline your first improvement goals.',
     24, 2, 'Frameworks for Effective Goal Setting and Progress Tracking', 35),

    -- Implementing Continuous Self-Improvement (course_content_id: 36)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to sustain improvement momentum through continuous self-assessment. This article covers accountability systems, habit-building, and long-term tracking. You will gain skills to maintain growth momentum. Action: Create a personal accountability plan.',
     21, 1, 'Sustaining Continuous Self-Improvement Through Accountability', 36),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore long-term success stories from continuous self-improvement. This video covers strategies for maintaining progress and measuring success. You will gain skills to sustain your growth efforts. Action: Identify one long-term metric to track your progress.',
     27, 2, 'Case Studies on Sustained Self-Improvement Success', 36),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will check their readiness for continuous self-improvement through this quiz. It covers accountability and tracking strategies, providing feedback on your knowledge. You will gain insights into areas for growth. Action: Complete the quiz and define your next steps.',
     14, 3, 'Interactive Quiz on Continuous Self-Improvement Strategies', 36);

-- Business Excellence Blueprint
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Laying the Foundations of Excellence', 1, 11),
    ('Establishing Continuous Improvement Systems', 2, 11),
    ('Embedding a High-Performance Culture', 3, 11);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Laying the Foundations of Excellence (course_content_id: 37)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn the core principles of organizational excellence, including strategic alignment and operational efficiency. This article covers frameworks for building a strong foundation and avoiding pitfalls. You will gain skills to assess your organization’s readiness. Action: Assess your current organizational foundation.',
     21, 1, 'Core Principles for Building Organizational Excellence', 37),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore how top companies build foundations for organizational excellence. This video covers case studies, strategic planning, and alignment strategies. You will gain skills to adopt best practices for excellence. Action: Identify one best practice to implement.',
     26, 2, 'Case Studies on Building Foundations for Excellence', 37),

    -- Establishing Continuous Improvement Systems (course_content_id: 38)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to create systems driving continuous improvement across their organization. This article covers improvement frameworks, feedback loops, and performance tracking. You will gain skills to design effective systems. Action: Design an improvement loop for your organization.',
     23, 1, 'Creating Systems for Continuous Organizational Improvement', 38),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore examples of continuous improvement initiatives in action. This video covers practical implementation steps and case studies. You will gain skills to launch improvement initiatives. Action: Define your first improvement initiative.',
     27, 2, 'Practical Examples of Continuous Improvement Initiatives', 38),

    -- Embedding a High-Performance Culture (course_content_id: 39)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to embed a culture valuing high performance. This article covers cultural values, leadership roles, and employee engagement strategies. You will gain skills to create a high-performance culture. Action: Outline your organization’s cultural values.',
     22, 1, 'Building a High-Performance Organizational Culture', 39),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore how to maintain momentum in a high-performance culture. This video covers case studies and strategies for cultural alignment. You will gain skills to sustain performance excellence. Action: Draft your culture plan.',
     28, 2, 'Sustaining Momentum in a High-Performance Culture', 39),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will assess their readiness to establish a culture of excellence through this quiz. It covers cultural alignment and engagement strategies, providing feedback on your knowledge. You will gain insights into areas for improvement. Action: Complete the quiz and review results.',
     14, 3, 'Interactive Quiz on Establishing a Culture of Excellence', 39);

-- Organizational Success Through Structure
INSERT INTO course_content (title, position, course_id)
VALUES
    ('Building Effective Team Structures', 1, 12),
    ('Optimizing Systems for Agility', 2, 12),
    ('Maintaining Long-Term Performance', 3, 12);

INSERT INTO course_lecture (content_text, content_type, content_file_name, description, duration_minutes, position, title, course_content_id)
VALUES
    -- Building Effective Team Structures (course_content_id: 40)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to design efficient team structures for clarity and accountability. This article covers organizational design principles, role definitions, and collaboration frameworks. You will gain skills to create effective team structures. Action: Map your current team structure.',
     21, 1, 'Designing Efficient Team Structures for Accountability', 40),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore case studies of successful team structures and collaboration. This video covers aligning teams for efficiency and accountability, with examples. You will gain skills to optimize team design. Action: Note one structural improvement for your team.',
     27, 2, 'Case Studies on High-Performing Team Structures', 40),

    -- Optimizing Systems for Agility (course_content_id: 41)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to design systems that adapt quickly to change. This article covers agility frameworks, process optimization, and adaptive strategies. You will gain skills to create agile systems. Action: Identify one agility enabler for your organization.',
     23, 1, 'Designing Agile Systems for Rapid Adaptation', 41),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will explore strategies for integrating agility into daily processes. This video covers case studies of agile organizations and implementation steps. You will gain skills to design agile systems. Action: Design an agility roadmap for your organization.',
     28, 2, 'Practical Strategies for Integrating Organizational Agility', 41),

    -- Maintaining Long-Term Performance (course_content_id: 42)
    ('Lorem Ipsum is simply dummy text...',
     'TEXT', NULL,
     'Users will learn to create processes supporting sustained organizational performance. This article covers performance monitoring, feedback loops, and continuous improvement strategies. You will gain skills to maintain long-term success. Action: Plan your continuous monitoring process.',
     22, 1, 'Creating Processes for Sustained Organizational Performance', 42),
    (NULL, 'VIDEO', 'https://www.youtube.com/watch?v=aeBaSrJmTMk&list=RDaeBaSrJmTMk&start_radio=1&ab_channel=AVAIONVEVO',
     'Users will learn to track, evaluate, and adjust systems for sustained success. This video covers performance metrics, case studies, and adaptation strategies. You will gain skills to sustain performance. Action: Define one key performance metric to track.',
     29, 2, 'Tracking and Adapting Systems for Long-Term Success', 42),
    (NULL, 'QUIZ', 'https://www.britannica.com/games/sudoku',
     'Users will check their readiness to sustain structural success through this quiz. It covers performance monitoring and adaptation strategies, providing feedback on your knowledge. You will gain insights into areas for improvement. Action: Complete the quiz and review results.',
     15, 3, 'Interactive Quiz on Sustaining Structural Success', 42);
