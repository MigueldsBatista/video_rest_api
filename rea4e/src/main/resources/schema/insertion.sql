USE VIDEO_REST_API;


INSERT INTO USUARIO (EMAIL, NOME, SENHA) VALUES
('user1@example.com', 'João Silva', 'senha123'),
('user2@example.com', 'Maria Oliveira', 'senha456'),
('user3@example.com', 'Carlos Souza', 'senha789'),
('user4@example.com', 'Fernanda Lima', 'senha101'),
('user5@example.com', 'Roberto Costa', 'senha202'),
('user6@example.com', 'Luciana Pereira', 'senha303'),
('user7@example.com', 'Ricardo Santos', 'senha404'),
('user8@example.com', 'Ana Souza', 'senha505'),
('user9@example.com', 'Paulo Oliveira', 'senha606'),
('user10@example.com', 'Juliana Costa', 'senha707'),
('msb2', 'Miguel Batista', '123');


INSERT INTO PERMISSOES(USUARIO_ID, PERMISSAO) VALUES
((SELECT ID FROM USUARIO WHERE EMAIL='msb2'), 'ADMIN');

INSERT INTO VIDEO (CATEGORIA, DESCRICAO, TITULO, URL, USUARIO_ID) VALUES
('BACKEND', 'Curso básico de Backend com Java.', 'Backend com Java', 'https://example.com/backend-java', 1),
('FRONTEND', 'Curso de desenvolvimento Frontend com React.', 'Frontend com React', 'https://example.com/frontend-react', 2),
('DESIGN', 'Fundamentos do Design Gráfico.', 'Design Gráfico Básico', 'https://example.com/design-basico', 3),
('DEVOPS', 'Introdução ao DevOps e automação.', 'DevOps na Prática', 'https://example.com/devops-pratica', 4),
('MACHINE_LEARNING', 'Aprenda Machine Learning com Python.', 'Machine Learning com Python', 'https://example.com/ml-python', 5),
('IA', 'Curso de Inteligência Artificial com TensorFlow.', 'Inteligência Artificial com TensorFlow', 'https://example.com/ia-tensorflow', 6),
('PROGRAMACAO', 'Fundamentos de programação em C.', 'Programação em C', 'https://example.com/programacao-c', 7),
('SEGURANCA', 'Introdução à segurança da informação.', 'Segurança da Informação', 'https://example.com/seguranca', 8),
('TESTES', 'Automatização de testes com Selenium.', 'Automatização de Testes', 'https://example.com/testes-selenium', 9),
('UX', 'Melhorando a experiência do usuário.', 'Experiência do Usuário', 'https://example.com/ux', 10);

INSERT INTO CURTIDAS (USUARIO_ID, VIDEO_ID) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(1, 3),
(2, 5),
(3, 7),
(4, 9),
(5, 6),
(6, 2),
(7, 8),
(8, 10),
(9, 1),
(10, 4);

INSERT INTO COMENTARIO (pergunta, USUARIO_ID, VIDEO_ID) VALUES
('Como posso melhorar meu código em Java?', 1, 1),
('Quais são as melhores práticas de CSS para React?', 2, 2),
('Quais ferramentas posso usar para edição gráfica?', 3, 3),
('Qual a melhor estratégia de implantação para o DevOps?', 4, 4),
('O que são algoritmos supervisionados em Machine Learning?', 5, 5),
('Como treinar uma rede neural em TensorFlow?', 6, 6),
('Como utilizar ponteiros em C?', 7, 7),
('Quais são os principais tipos de ataques cibernéticos?', 8, 8),
('Como criar testes automatizados com Selenium?', 9, 9),
('Como realizar uma pesquisa de UX com usuários?', 10, 10);

