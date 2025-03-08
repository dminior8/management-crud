INSERT INTO product (id, name, description, category, stock, price, created_at) VALUES
                                                                                    (gen_random_uuid(), 'Laptop Lenovo', 'Wydajny laptop do pracy biurowej', 'Elektronika', 10, 3999.99, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Smartfon Samsung', 'Nowoczesny smartfon z 5G', 'Elektronika', 25, 2899.00, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Telewizor LG', '50-calowy telewizor 4K UHD', 'Elektronika', 5, 2499.50, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'PS5 Konsola', 'Nowa generacja konsol do gier', 'Elektronika', 8, 2999.00, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Słuchawki Sony', 'Słuchawki z redukcją szumów', 'Elektronika', 15, 799.99, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Ekspres Kawa', 'Ekspres ciśnieniowy do kawy', 'Elektronika', 12, 1499.90, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Lodówka Samsung', 'Nowoczesna lodówka z funkcją No Frost', 'Elektronika', 6, 3499.00, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Robot Xiaomi', 'Automatyczny odkurzacz z aplikacją', 'Elektronika', 20, 1799.00, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Monitor Dell', 'Monitor gamingowy 144Hz', 'Elektronika', 30, 1299.00, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Książka Java', 'Podręcznik do nauki języka Java', 'Książki', 50, 99.99, CURRENT_TIMESTAMP),
                                                                                    (gen_random_uuid(), 'Bluza Nike', 'Sportowa bluza z kapturem', 'Odzież', 40, 199.99, CURRENT_TIMESTAMP);

INSERT INTO blocked_word (id, name) VALUES
                                        (gen_random_uuid(), 'spam'),
                                        (gen_random_uuid(), 'oszustwo'),
                                        (gen_random_uuid(), 'podróbka'),
                                        (gen_random_uuid(), 'scam'),
                                        (gen_random_uuid(), 'fałszywka'),
                                        (gen_random_uuid(), 'hack'),
                                        (gen_random_uuid(), 'niedozwolone'),
                                        (gen_random_uuid(), 'zakazane'),
                                        (gen_random_uuid(), 'zbanowane'),
                                        (gen_random_uuid(), 'nielegalne');
