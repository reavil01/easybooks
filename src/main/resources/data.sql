INSERT INTO company (number, name, owner, address, type, items, email, phone, fax) VALUES
    ('1234567890', '페이퍼회사', '나', '어딘가', '종이', '종이', 'abc@gmail.com', '00000000000', '11111111111');
INSERT INTO ledger (company_number, type, date, item, unit_price, quantity, price, vat, total) VALUES
    ('1234567890', 0, '2021-05-09', '종이', 50, 100, 5000, 500, 5500);
INSERT INTO transaction (company_number, date, price, type) VALUES
    ('1234567890', '2021-05-09', 100, 0),
    ('1234567890', '2021-05-10', 100, 0);
