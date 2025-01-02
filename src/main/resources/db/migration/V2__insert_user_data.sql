INSERT INTO users (id, email, password) VALUES
('40e0ff36-c61d-4b95-b12b-1ae631626276', 'akbar@example.com', '123456'),
('2477e831-aa6d-4f7f-a23e-23d5152258aa', 'sitamvan@example.com', '123456')
ON CONFLICT (id) DO NOTHING;

INSERT INTO user_roles (user_id, role) VALUES
('40e0ff36-c61d-4b95-b12b-1ae631626276', 'admin'),
('2477e831-aa6d-4f7f-a23e-23d5152258aa', 'user'),
('2477e831-aa6d-4f7f-a23e-23d5152258aa', 'user')
ON CONFLICT (user_id, role) DO NOTHING;