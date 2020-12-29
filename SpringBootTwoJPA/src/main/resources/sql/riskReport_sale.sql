drop table if  exists riskreport_salemain;
CREATE TABLE
    riskreport_salemain
    (
        archivesno CHAR(22) NOT NULL,
        explorecomcode CHAR(8) NOT NULL,
        explorer VARCHAR(10) NOT NULL,
        comcode VARCHAR(8),
        checkupcode VARCHAR(10),
        companyname VARCHAR(100),
        exploreaddress VARCHAR(120),
        exploredate DATE,
        checkupflag CHAR(1) NOT NULL,
        mobileflag CHAR(1) NOT NULL,
        inserttimeforhis TIMESTAMP(6),
        operatetimeforhis TIMESTAMP(6),
        PRIMARY KEY (archivesno)
    );
    drop table if  exists riskreport_saleimatype;
    CREATE TABLE riskreport_saleimatype
    (
        archivesno CHAR(22) NOT NULL,
        imagetype VARCHAR(10) NOT NULL,
        typecname VARCHAR(50),
        imagesum INTEGER NOT NULL,
        imagerepulsesum INTEGER NOT NULL,
        remark VARCHAR(300),
        inserttimeforhis  TIMESTAMP(6),
        operatetimeforhis  TIMESTAMP(6),
        PRIMARY KEY (archivesno, imagetype) 
    );
    
    
