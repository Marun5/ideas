INSERT INTO categories (id, name) VALUES
          (gen_random_uuid(), 'Zdrowie'),
          (gen_random_uuid(), 'Turystyka'),
          (gen_random_uuid(), 'Zarobki'),
          (gen_random_uuid(), 'Rozrywka'),
          (gen_random_uuid(), 'Sport'),
          (gen_random_uuid(), 'Edukacja'),
          (gen_random_uuid(), 'Kulinaria'),
          (gen_random_uuid(), 'Książka'),
          (gen_random_uuid(), 'Film'),
          (gen_random_uuid(), 'IT'),
          (gen_random_uuid(), 'Mechanika'),
          (gen_random_uuid(), 'Zwierzęta');

insert into questions (id, name, category_id) values
      (gen_random_uuid(), 'Co zwiedzić w Paryżu?', (select id from categories where name = 'Turystyka')),
      (gen_random_uuid(), 'Co zwiedzić we Włoszech?', (select id from categories where name = 'Turystyka')),
      (gen_random_uuid(), 'Jakie są zasady gry w squasha?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'Jakie są wymiary boiska do gry w tenisa?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'Kto wygrał mistrzostwa świata w piłce nożnej w Meksyku w 1986 roku?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'Ile waży piłka do golfa?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'Nowości 2023', (select id from categories where name = 'Film')),
      (gen_random_uuid(), 'Jakie znasz drapieżne zwierzę?', (select id from categories where name = 'Zwierzęta')),
      (gen_random_uuid(), 'Jakie są najzdrowsze witaminy?', (select id from categories where name = 'Zdrowie')),
      (gen_random_uuid(), 'Jak często powinno się uprawiać sport?', (select id from categories where name = 'Zdrowie'));

insert into answers (id, name, question_id) values
      (gen_random_uuid(), 'Przynajmniej trzy razy w tygodniu', (select id from questions where name = 'Jak często powinno się uprawiać sport?')),
      (gen_random_uuid(), 'Najlepiej codziennie', (select id from questions where name = 'Jak często powinno się uprawiać sport?')),
      (gen_random_uuid(), 'Gepard', (select id from questions where name = 'Jakie znasz drapieżne zwierzę?')),
      (gen_random_uuid(), 'Lew', (select id from questions where name = 'Jakie znasz drapieżne zwierzę?')),
      (gen_random_uuid(), 'Tygrys', (select id from questions where name = 'Jakie znasz drapieżne zwierzę?')),
      (gen_random_uuid(), 'Aligator', (select id from questions where name = 'Jakie znasz drapieżne zwierzę?')),
      (gen_random_uuid(), 'Rekin', (select id from questions where name = 'Jakie znasz drapieżne zwierzę?'));

insert into answers (id, name, question_id) values
    (gen_random_uuid(), 'Pola Elizejskie', (select id from questions where name = 'Co zwiedzić w Paryżu?')),
    (gen_random_uuid(), 'Wieża Eiffla', (select id from questions where name = 'Co zwiedzić w Paryżu?')),
    (gen_random_uuid(), 'Piza', (select id from questions where name = 'Co zwiedzić we Włoszech?')),
    (gen_random_uuid(), 'Neapol', (select id from questions where name = 'Co zwiedzić we Włoszech?')),
    (gen_random_uuid(), 'Rzym', (select id from questions where name = 'Co zwiedzić we Włoszech?')),
    (gen_random_uuid(), 'Wenecja', (select id from questions where name = 'Co zwiedzić we Włoszech?'));