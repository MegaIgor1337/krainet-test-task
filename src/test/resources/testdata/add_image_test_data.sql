INSERT INTO file (name, content)
VALUES ('image-test.png', pg_read_binary_file('images/image-test.png'));
