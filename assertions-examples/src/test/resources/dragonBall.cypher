CREATE  (:Character:Hero {name : 'Son Goku'});
CREATE  (:Character:Hero {name : 'Bulma'});
CREATE  (:Character:Hero:Master {name : 'Master Roshi'});
CREATE  (:Character:Villain:Master {name : 'Master Shen'});
CREATE  (:Character:Hero:Master {name : 'Master Mutaito'});
CREATE  (:Character:Hero {name : 'Yamcha'});
CREATE  (:Character:Hero {name : 'Krillin'});
CREATE  (:Character:Hero {name : 'Tien Shinhan'});
CREATE  (:Character:Hero {name : 'Chiaotzu'});

CREATE  (:Character:Hero:Villain {name : 'Piccolo'});
CREATE  (:Character:Hero:Villain {name : 'Vegeta'});

CREATE  (:Character:Villain {name : 'Commander Red'});
CREATE  (:Character:Villain {name : 'Dr. Gero'});
CREATE  (:Character:Villain {name : 'Dr. Flappe'});

CREATE  (:Character:Villain:Android {name : 'Android 8'});
CREATE  (:Character:Villain:Android {name : 'Android 17'});
CREATE  (:Character:Hero:Villain:Android {name : 'Android 18'});
CREATE  (:Character:Villain:Android {name : 'Cell'});

CREATE  (:Organization {name : 'Red Ribbon Army'});
CREATE  (:Organization {name : 'Planet Trade Organization'});

MATCH   (sonGoku:Character:Hero {name : 'Son Goku'}),
        (yamcha:Character:Hero {name : 'Yamcha'}),
        (krillin:Character:Hero {name : 'Krillin'}),
        (tien_shinhan:Character:Hero {name : 'Tien Shinhan'}),
        (chiaotzu:Character:Hero {name : 'Chiaotzu'}),
        (master_roshi:Character:Hero:Master {name : 'Master Roshi'}),
        (master_shen:Character:Villain:Master {name : 'Master Shen'}),
        (master_mutaito:Character:Hero:Master {name : 'Master Mutaito'})
CREATE  (sonGoku)-[sonGoku_master_roshi:HAS_TRAINED_WITH]->(master_roshi),
        (krillin)-[krillin_master_roshi:HAS_TRAINED_WITH]->(master_roshi),
        (yamcha)-[yamcha_master_roshi:HAS_TRAINED_WITH]->(master_roshi),
        (tien_shinhan)-[tien_shinhan_master_shen:HAS_TRAINED_WITH]->(master_shen),
        (chiaotzu)-[chiaotzu_master_shen:HAS_TRAINED_WITH]->(master_shen),
        (master_roshi)-[master_roshi_master_mutaito:HAS_TRAINED_WITH]->(master_mutaito),
        (master_shen)-[master_shen_master_mutaito:HAS_TRAINED_WITH]->(master_mutaito)
RETURN  sonGoku_master_roshi, krillin_master_roshi, yamcha_master_roshi,
        tien_shinhan_master_shen, chiaotzu_master_shen,
        master_roshi_master_mutaito, master_shen_master_mutaito;


MATCH   (commander:Character:Villain {name : 'Commander Red'}),
        (drGero:Character:Villain {name : 'Dr. Gero'}),
        (drFlappe:Character:Villain {name : 'Dr. Flappe'}),
        (android8:Character:Villain:Android {name : 'Android 8'}),
        (redRibbon:Organization {name : 'Red Ribbon Army'})
CREATE  (commander)-[commander_rr:HAS_WORKED_FOR]->(redRibbon),
        (drGero)-[drGero_rr:HAS_WORKED_FOR]->(redRibbon),
        (drFlappe)-[drFlappe_rr:HAS_WORKED_FOR]->(redRibbon),
        (android8)-[android8_rr:HAS_WORKED_FOR]->(redRibbon)
RETURN  commander_rr, drGero_rr, drFlappe_rr, android8_rr;

MATCH   (vegeta:Character:Villain {name : 'Vegeta'}),
        (planetTrade:Organization {name : 'Planet Trade Organization'})
CREATE  (vegeta)-[vegeta_planetTrade:HAS_WORKED_FOR {past: true}]->(planetTrade)
RETURN  vegeta_planetTrade;

MATCH   (drGero:Character:Villain {name : 'Dr. Gero'}),
        (drFlappe:Character:Villain {name : 'Dr. Flappe'}),
        (android8:Character:Villain:Android {name : 'Android 8'}),
        (android17:Character:Villain:Android {name : 'Android 17'}),
        (android18:Character:Villain:Android {name : 'Android 18'}),
        (cell:Character:Villain:Android {name : 'Cell'})
CREATE  (drFlappe)-[drFlappe_android8:PRODUCED]->(android8),
        (drGero)-[drGero_android17:PRODUCED]->(android17),
        (drGero)-[drGero_android18:PRODUCED]->(android18),
        (drGero)-[drGero_cell:PRODUCED]->(cell)
RETURN  drFlappe_android8, drGero_android17, drGero_android18, drGero_cell;

MATCH   (krillin:Character:Hero {name : 'Krillin'}),
        (android18:Character:Hero:Villain:Android {name : 'Android 18'}),
        (bulma:Character:Hero {name : 'Bulma'}),
        (vegeta:Character:Hero:Villain {name : 'Vegeta'})
CREATE  (krillin)-[krillin_android18:IS_MARRIED_TO]->(android18),
        (vegeta)-[vegeta_bulma:IS_MARRIED_TO]->(bulma)
RETURN  krillin_android18, vegeta_bulma;

MATCH   (sonGoku:Character:Hero {name : 'Son Goku'}),
        (vegeta:Character:Villain {name : 'Vegeta'}),
        (krillin:Character:Hero {name : 'Krillin'}),
        (piccolo:Character:Hero:Villain {name : 'Piccolo'}),
        (tien_shinhan:Character:Hero {name : 'Tien Shinhan'}),
        (yamcha:Character:Hero {name : 'Yamcha'})
CREATE  (sonGoku)-[gogeta:IN_FUSION_WITH {fusion_character_name: 'Gogeta'}]->(vegeta),
        (sonGoku)-[veku:IN_FUSION_WITH {fusion_character_name: 'Veku', useless: true}]->(vegeta),
        (krillin)-[prilin:IN_FUSION_WITH {fusion_character_name: 'Prilin', useless: true}]->(piccolo),
        (tien_shinhan)-[tiencha:IN_FUSION_WITH {fusion_character_name: 'Tiencha'}]->(yamcha)
RETURN  gogeta, veku, prilin, tiencha;