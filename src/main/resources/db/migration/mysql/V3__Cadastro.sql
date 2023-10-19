INSERT INTO `usuario` (`id`, `nome`, `email`, `senha`, `telefone`, `ativo`) VALUES
(DEFAULT, 'DanielFB', 'danielbaranalt@gmail.com', '$2a$10$FHayM6spzm5LGUa//VKYKe9iWLPlSnYpdwGEkvHMlCEZUIsr4EEIG', '43984634146', TRUE);

INSERT INTO `regra` (`id`, `nome`, `descricao`, `ativo`) VALUES
(DEFAULT, 'ROLE_USUARIO', 'Permite acesso aos serviços de usuário', TRUE);

INSERT INTO `usuario_regra` (`usuario_id`, `regra_id`) VALUES (
(SELECT `id` FROM usuario WHERE email = 'danielbaranalt@gmail.com'),
(SELECT `id` FROM regra WHERE nome = 'ROLE_USUARIO')
);