INSERT INTO categories (id, name) VALUES
          (gen_random_uuid(), 'Health'),
          (gen_random_uuid(), 'Travel'),
          (gen_random_uuid(), 'Money'),
          (gen_random_uuid(), 'Fun'),
          (gen_random_uuid(), 'Sport'),
          (gen_random_uuid(), 'Education'),
          (gen_random_uuid(), 'Cooking'),
          (gen_random_uuid(), 'Books'),
          (gen_random_uuid(), 'Film'),
          (gen_random_uuid(), 'IT'),
          (gen_random_uuid(), 'Music'),
          (gen_random_uuid(), 'Animals');

insert into questions (id, name, category_id) values
      (gen_random_uuid(), 'What to visit in Paris?', (select id from categories where name = 'Travel')),
      (gen_random_uuid(), 'What to visit in Italy?', (select id from categories where name = 'Travel')),
      (gen_random_uuid(), 'What is your favourite type of food?', (select id from categories where name = 'Cooking')),
      (gen_random_uuid(), 'What are the rules of squash?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'What are the dimensions of the tennis cort?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'Who is your favourite volleyball player?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'Who won football World Cup in Mexico in 1986?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'How much does a golf ball weigh?', (select id from categories where name = 'Sport')),
      (gen_random_uuid(), 'New movies in 2023', (select id from categories where name = 'Film')),
      (gen_random_uuid(), 'Instruments you like to play on', (select id from categories where name = 'Music')),
      (gen_random_uuid(), 'What predatory animals do you know?', (select id from categories where name = 'Animals')),
      (gen_random_uuid(), 'What are the most healthy vitamins?', (select id from categories where name = 'Health')),
      (gen_random_uuid(), 'How often should you exercise?', (select id from categories where name = 'Health'));

insert into answers (id, name, question_id) values
      (gen_random_uuid(), 'At least three times a week', (select id from questions where name = 'How often should you exercise?')),
      (gen_random_uuid(), 'Everyday is perfect', (select id from questions where name = 'How often should you exercise?')),
      (gen_random_uuid(), 'Italian cuisine', (select id from questions where name = 'What is your favourite type of food?')),
      (gen_random_uuid(), 'Pizza', (select id from questions where name = 'What is your favourite type of food?')),
      (gen_random_uuid(), 'Fast food', (select id from questions where name = 'What is your favourite type of food?')),
      (gen_random_uuid(), 'Drums', (select id from questions where name = 'Instruments you like to play on')),
      (gen_random_uuid(), 'Guitar', (select id from questions where name = 'Instruments you like to play on')),
      (gen_random_uuid(), 'Violin', (select id from questions where name = 'Instruments you like to play on')),
      (gen_random_uuid(), 'Cheetah', (select id from questions where name = 'What predatory animals do you know?')),
      (gen_random_uuid(), 'Lion', (select id from questions where name = 'What predatory animals do you know?')),
      (gen_random_uuid(), 'Tiger', (select id from questions where name = 'What predatory animals do you know?')),
      (gen_random_uuid(), 'Alligator', (select id from questions where name = 'What predatory animals do you know?')),
      (gen_random_uuid(), 'Shark', (select id from questions where name = 'What predatory animals do you know?'));

insert into answers (id, name, question_id) values
    (gen_random_uuid(), 'Avenue des Champs-Elysees', (select id from questions where name = 'What to visit in Paris?')),
    (gen_random_uuid(), 'Eiffel Tower', (select id from questions where name = 'What to visit in Paris?')),
    (gen_random_uuid(), 'Piza', (select id from questions where name = 'What to visit in Italy?')),
    (gen_random_uuid(), 'Napoli', (select id from questions where name = 'What to visit in Italy?')),
    (gen_random_uuid(), 'Rome', (select id from questions where name = 'What to visit in Italy?')),
    (gen_random_uuid(), 'Venice', (select id from questions where name = 'What to visit in Italy?'));

insert into questions (id, name, category_id) values
      (gen_random_uuid(), 'Any good crime books?', (select id from categories where name = 'Books')),
      (gen_random_uuid(), 'Which romance books do you recommend?', (select id from categories where name = 'Books'));

insert into answers (id, name, question_id) values
    (gen_random_uuid(), 'The Cat Who Caught a Killer', (select id from questions where name = 'Any good crime books?')),
    (gen_random_uuid(), 'Dirty Town', (select id from questions where name = 'Any good crime books?')),
    (gen_random_uuid(), 'Wilfredo Leon', (select id from questions where name = 'Who is your favourite volleyball player?')),
    (gen_random_uuid(), 'Kamil Semeniuk', (select id from questions where name = 'Who is your favourite volleyball player?')),
    (gen_random_uuid(), 'Paweł Zatorski', (select id from questions where name = 'Who is your favourite volleyball player?')),
    (gen_random_uuid(), 'Bartosz Kurek', (select id from questions where name = 'Who is your favourite volleyball player?'));