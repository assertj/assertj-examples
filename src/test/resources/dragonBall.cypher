CREATE  (n:CHARACTER:HERO {name : 'Son Goku'}) RETURN n;
CREATE  (n:CHARACTER:HERO {name : 'Bulma'}) RETURN n;
CREATE  (n:CHARACTER:HERO:MASTER {name : 'Master Roshi'}) RETURN n;
CREATE  (n:CHARACTER:VILLAIN:MASTER {name : 'Master Shen'}) RETURN n;
CREATE  (n:CHARACTER:HERO:MASTER {name : 'Master Mutaito'}) RETURN n;
CREATE  (n:CHARACTER:HERO {name : 'Yamcha'}) RETURN n;
CREATE  (n:CHARACTER:HERO {name : 'Krillin'}) RETURN n;
CREATE  (n:CHARACTER:HERO {name : 'Tien Shinhan'}) RETURN n;
CREATE  (n:CHARACTER:HERO {name : 'Chiaotzu'}) RETURN n;

CREATE  (n:CHARACTER:HERO:VILLAIN {name : 'Piccolo'}) RETURN n;
CREATE  (n:CHARACTER:HERO:VILLAIN {name : 'Vegeta'}) RETURN n;

CREATE  (n:CHARACTER:VILLAIN {name : 'Commander Red'}) RETURN n;
CREATE  (n:CHARACTER:VILLAIN {name : 'Dr. Gero'}) RETURN n;
CREATE  (n:CHARACTER:VILLAIN {name : 'Dr. Flappe'}) RETURN n;

CREATE  (n:CHARACTER:VILLAIN:ANDROID {name : 'Android 8'}) RETURN n;
CREATE  (n:CHARACTER:VILLAIN:ANDROID {name : 'Android 17'}) RETURN n;
CREATE  (n:CHARACTER:HERO:VILLAIN:ANDROID {name : 'Android 18'}) RETURN n;
CREATE  (n:CHARACTER:VILLAIN:ANDROID {name : 'Cell'}) RETURN n;

CREATE  (n:ORGANIZATION {name : 'Red Ribbon Army'}) RETURN n;
CREATE  (n:ORGANIZATION {name : 'Planet Trade Organization'}) RETURN n;

MATCH   (sonGoku:CHARACTER:HERO {name : 'Son Goku'}),
        (yamcha:CHARACTER:HERO {name : 'Yamcha'}),
        (krillin:CHARACTER:HERO {name : 'Krillin'}),
        (tien_shinhan:CHARACTER:HERO {name : 'Tien Shinhan'}),
        (chiaotzu:CHARACTER:HERO {name : 'Chiaotzu'}),
        (master_roshi:CHARACTER:HERO:MASTER {name : 'Master Roshi'}),
        (master_shen:CHARACTER:VILLAIN:MASTER {name : 'Master Shen'}),
        (master_mutaito:CHARACTER:HERO:MASTER {name : 'Master Mutaito'})
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


MATCH   (commander:CHARACTER:VILLAIN {name : 'Commander Red'}),
        (drGero:CHARACTER:VILLAIN {name : 'Dr. Gero'}),
        (drFlappe:CHARACTER:VILLAIN {name : 'Dr. Flappe'}),
        (android8:CHARACTER:VILLAIN:ANDROID {name : 'Android 8'}),
        (redRibbon:ORGANIZATION {name : 'Red Ribbon Army'})
CREATE  (commander)-[commander_rr:HAS_WORKED_FOR]->(redRibbon),
        (drGero)-[drGero_rr:HAS_WORKED_FOR]->(redRibbon),
        (drFlappe)-[drFlappe_rr:HAS_WORKED_FOR]->(redRibbon),
        (android8)-[android8_rr:HAS_WORKED_FOR]->(redRibbon)
RETURN  commander_rr, drGero_rr, drFlappe_rr, android8_rr;

MATCH   (vegeta:CHARACTER:VILLAIN {name : 'Vegeta'}),
        (planetTrade:ORGANIZATION {name : 'Planet Trade Organization'})
CREATE  (vegeta)-[vegeta_planetTrade:HAS_WORKED_FOR {past: true}]->(planetTrade)
RETURN  vegeta_planetTrade;

MATCH   (drGero:CHARACTER:VILLAIN {name : 'Dr. Gero'}),
        (drFlappe:CHARACTER:VILLAIN {name : 'Dr. Flappe'}),
        (android8:CHARACTER:VILLAIN:ANDROID {name : 'Android 8'}),
        (android17:CHARACTER:VILLAIN:ANDROID {name : 'Android 17'}),
        (android18:CHARACTER:VILLAIN:ANDROID {name : 'Android 18'}),
        (cell:CHARACTER:VILLAIN:ANDROID {name : 'Cell'})
CREATE  (drFlappe)-[drFlappe_android8:PRODUCED]->(android8),
        (drGero)-[drGero_android17:PRODUCED]->(android17),
        (drGero)-[drGero_android18:PRODUCED]->(android18),
        (drGero)-[drGero_cell:PRODUCED]->(cell)
RETURN  drFlappe_android8, drGero_android17, drGero_android18, drGero_cell;

MATCH   (krillin:CHARACTER:HERO {name : 'Krillin'}),
        (android18:CHARACTER:HERO:VILLAIN:ANDROID {name : 'Android 18'}),
        (bulma:CHARACTER:HERO {name : 'Bulma'}),
        (vegeta:CHARACTER:HERO:VILLAIN {name : 'Vegeta'})
CREATE  (krillin)-[krillin_android18:IS_MARRIED_TO]->(android18),
        (vegeta)-[vegeta_bulma:IS_MARRIED_TO]->(bulma)
RETURN  krillin_android18, vegeta_bulma;

MATCH   (sonGoku:CHARACTER:HERO {name : 'Son Goku'}),
        (vegeta:CHARACTER:VILLAIN {name : 'Vegeta'}),
        (krillin:CHARACTER:HERO {name : 'Krillin'}),
        (piccolo:CHARACTER:HERO:VILLAIN {name : 'Piccolo'}),
        (tien_shinhan:CHARACTER:HERO {name : 'Tien Shinhan'}),
        (yamcha:CHARACTER:HERO {name : 'Yamcha'})
CREATE  (sonGoku)-[gogeta:IN_FUSION_WITH {into: 'Gogeta'}]->(vegeta),
        (sonGoku)-[veku:IN_FUSION_WITH {into: 'Veku', useless: true}]->(vegeta),
        (krillin)-[prilin:IN_FUSION_WITH {into: 'Prilin', useless: true}]->(piccolo),
        (tien_shinhan)-[tiencha:IN_FUSION_WITH {into: 'Tiencha'}]->(yamcha)
RETURN  gogeta, veku, prilin, tiencha;