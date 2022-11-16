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
        (gen_random_uuid(), 'Jakie są najzdrowsze witaminy?', (select id from categories where name = 'Zdrowie')),
        (gen_random_uuid(), 'Jak często powinno się uprawiać sport?', (select id from categories where name = 'Zdrowie'));

insert into answers (id, name, question_id) values
      (gen_random_uuid(), 'Przynajmniej trzy razy w tygodniu', (select id from questions where name = 'Jak często powinno się uprawiać sport?')),
      (gen_random_uuid(), 'Najlepiej codziennie', (select id from questions where name = 'Jak często powinno się uprawiać sport?'));