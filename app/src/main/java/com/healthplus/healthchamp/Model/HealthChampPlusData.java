package com.healthplus.healthchamp.Model;

import java.io.Serializable;

public class HealthChampPlusData implements Serializable {
    public String name;
    public String fname;
    public String mname;
    public String id;
    private String editTextValue;
    public String special;
    public String userregid;
    public String leavetable;
    public String competency;
    public String language;
    public String startdate;
    public String enddate;
    public String emailid;
    public String contact;
    public String gender;
    public String userid;
    public String password;
    public String trainingtype;
    public String code;
    public String date;
    public String state;
    public String district;
    public String block;
    public String tostate;
    public String todistrict;
    public String todate;
    public String remarks;
    public String images;
    public String document;
    public String todaytask;
    public String documentExcel;
    private boolean selected;
    public String trainerimage;
    public String status;
    public String no_of_books;
    public String attendencesheet;
    public String reasonapply;
    public String detailreason;
    public String commentsc;
    public String commentho;
    public String month;
    public String imp;
    public String session;
    public String nosession;
    public String video;
    public String recommendstartdate;
    public String recommendenddate;
    public String approvestartdate;
    public String stclass;
    public String approveenddate;
    public String adhar;
    public String middlename;
    public String lastname;
    public String categoryid;
    public String religionid;
    public String address;
    public String genderid;
    public String admissionno;
    public String classid;
    public String sectionid;
    public String disabilityid;
    public String uid;
    public String parentmobile;
    public String studentmobile;
    public String accno;
    public String ifsc;
    public String checkupdate;
    public String doctorname;
    public String doctorno;
    public String dentalcaries;
    public String rootstumps;
    public String stains;
    public String misaligned;
    public String oral;
    public String flouride;
    public String extraction;
    public String brushtwice;
    public String oralhygiene;
    public String other;
    public String orthodontics;
    public String restoration;
    public String advise;
    public String filetype;
    public String filepath;
    public String link;
    public String qualify;
    public String experience;


    public String issue;
    public String behave;
    public String input;
    public String history;
    public String counselling;

    public HealthChampPlusData() {
    }
    public HealthChampPlusData(boolean selected, String editTextValue, String issue, String behave, String input, String history, String counselling, String qualify, String experience, String special, String link, String filetype, String checkupdate, String doctorname, String doctorno, String dentalcaries, String rootstumps, String stains, String misaligned, String oral, String flouride, String extraction, String brushtwice, String oralhygiene, String other, String orthodontics, String restoration, String advise, String accno, String ifsc, String studentmobile, String parentmobile, String uid, String disabilityid, String sectionid, String classid, String admissionno, String genderid, String address, String categoryid, String religionid, String lastname, String middlename, String adhar, String fname, String name, String trainingtype, String code, String date, String state, String district, String block, String tostate, String todistrict, String todate, String remarks, String images, String document, String todaytask, String documentExcel, String competency, String language, String startdate, String emailid, String contact, String gender, String userid, String password, String trainerimage, String enddate, String status, String no_of_books, String attendencesheet, String reasonapply, String detailreason, String commentsc, String commentho, String month, String imp, String session, String nosession, String video, String id, String leavetable, String recommendstartdate, String recommendenddate, String approvestartdate, String approveenddate, String userregid, String mname, String stclass) {
       this.issue=issue;
       this.behave=behave;
        this.selected = selected;
       this.input=input;
       this.history=history;
       this.counselling=counselling;
        this.editTextValue=editTextValue;
        this.link=link;
        this.qualify=qualify;
        this.experience=experience;
        this.special=special;
        this.filepath = filepath;
        this.filetype=filetype;
        this.name = name;
        this.accno=accno;
        this.ifsc=ifsc;
        this.studentmobile=studentmobile;
        this.parentmobile=parentmobile;
        this.uid=uid;
        this.disabilityid=disabilityid;
        this.sectionid=sectionid;
        this.classid=classid;
        this.admissionno=admissionno;
        this.address=address;
        this.genderid=genderid;
        this.categoryid=categoryid;
        this.religionid=religionid;
        this.lastname=lastname;
        this.middlename=middlename;
        this.adhar=adhar;
        this.stclass=stclass;
        this.fname = fname;
        this.mname=mname;
        this.userregid=userregid;
        this.code=code;
        this.date=date;
        this.state=state;
        this.district=district;
        this.tostate=tostate;
        this.todistrict=todistrict;
        this.todate=todate;
        this.remarks=remarks;
        this.images=images;
        this.document=document;
        this.todaytask=todaytask;
        this.documentExcel=documentExcel;
        this.block=block;
        this.trainingtype=trainingtype;
        this.competency=competency;
        this.language=language;
        this.startdate=startdate;
        this.emailid=emailid;
        this.contact=contact;
        this.gender=gender;
        this.userid=userid;
        this.password=password;
        this.trainerimage=trainerimage;
        this.enddate=enddate;
        this.status=status;
        this.no_of_books=no_of_books;
        this.attendencesheet=attendencesheet;
        this.reasonapply=reasonapply;
        this.detailreason=detailreason;
        this.commentsc=commentsc;
        this.commentho=commentho;
        this.video=video;
        this.month=month;
        this.imp=imp;
        this.session=session;
        this.nosession=nosession;
        this.id=id;
        this.leavetable=leavetable;
        this.recommendstartdate=recommendstartdate;
        this.recommendenddate=recommendenddate;
        this.approvestartdate=approvestartdate;
        this.approveenddate=approveenddate;
        this.checkupdate=checkupdate;
        this.doctorname=doctorname;
        this.doctorno=doctorno;
        this.dentalcaries=dentalcaries;
        this.rootstumps=rootstumps;
        this.stains=stains;
        this.misaligned=misaligned;
        this.oral=oral;
        this.flouride=flouride;
        this.extraction=extraction;
        this.brushtwice=brushtwice;
        this.oralhygiene=oralhygiene;
        this.other=other;
        this.orthodontics=orthodontics;
        this.restoration=restoration;
        this.advise=advise;
    }
    public void setIssue(String issue)
    {
        this.issue=issue;
    }
    public void setBehave(String issue)
    {
        this.behave=behave;
    }
    public void setInput(String input)
    {
        this.input=input;
    }
    public void setHistory(String history)
    {
        this.history=history;
    }

    public void setLink(String link)
    {
        this.link=link;
    }
    public void setQualify(String qualify)
    {
        this.qualify=qualify;
    }
    public void setExperience(String experience)
    {
        this.experience=experience;
    }
    public void setSpecial(String special)
    {
        this.special=special;
    }
    public void setFiletype(String filetype)
    {
        this.filetype = filetype;
    }
    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }
    public void setUid(String uid)
    {
        this.uid = uid;
    }
    public void setAccno(String accno)
    {
        this.accno = accno;
    }

    public void setFName(String fname)
    {
        this.fname = fname;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
    public void setMiddlename(String middlename)
    {
        this.middlename = middlename;
    }
    public void setName(String name)
    {
        this.name = name;
    }



    public String setDate(String date)
    {
        this.date = date;

        return date;
    }

    public String setRemarks(String remarks)
    {
        this.remarks = remarks;

        return remarks;
    }
    public String setImages(String images)
    {
        this.images = images;

        return images;
    }
    public String setDocument(String document)
    {
        this.document = document;

        return document;
    }

    public String setContact(String contact)
    {
        this.contact=contact;
        return contact;
    }

    public String setStatus(String status)
    {
        this.status=status;
        return status;
    }

    public String setDetailReason(String detailreason)
    {
        this.detailreason=detailreason;
        return detailreason;
    }

    public String setSession(String session)
    {
        this.session=session;
        return session;
    }
    public String setId(String id)
    {
        this.id=id;
        return id;
    }

    public void setCheckupdate(String checkupdate)
    {
        this.checkupdate = checkupdate;
    }
    public void setDoctorname(String doctorname)
    {
        this.doctorname = doctorname;
    }
    public void setDoctorno(String doctorno)
    {
        this.doctorno = doctorno;
    }
    public void setDentalcaries(String dentalcaries)
    {
        this.dentalcaries = dentalcaries;
    }
    public void setRootstumps(String rootstumps)
    {
        this.rootstumps = rootstumps;
    }
    public void setStains(String stains)
    {
        this.stains = stains;
    }
    public void setMisaligned(String misaligned)
    {
        this.misaligned = misaligned;
    }
    public void setOral(String oral)
    {
        this.oral = oral;
    }
    public void setFlouride(String flouride)
    {
        this.flouride = flouride;
    }
    public void setExtraction(String extraction)
    {
        this.extraction = extraction;
    }
    public void setBrushtwice(String brushtwice)
    {
        this.brushtwice = brushtwice;
    }
    public void setOralhygiene(String oralhygiene)
    {
        this.oralhygiene = oralhygiene;
    }
    public void setOther(String other)
    {
        this.other = other;
    }
    public void setOrthodontics(String orthodontics)
    {
        this.orthodontics = orthodontics;
    }
    public void setRestoration(String restoration)
    {
        this.restoration = restoration;
    }
    public void setAdvise(String advise)
    {
        this.advise = advise;
    }
    public String getIssue()
    {
        return issue;
    }
    public String getBehave()
    {
        return behave;
    }
    public String getInput()
    {
        return input;
    }
    public String getHistory()
    {
        return history;
    }
    public String getSpecial()
    {
        return special;
    }
    public String getQualify()
    {
        return qualify;
    }
    public String getExperience()
    {
        return experience;
    }
    public String getLink()
    {
        return link;
    }
    public String getEditTextValue() {
        return editTextValue;
    }

    public String getFiletype()
    {
        return filetype;
    }
    public String getCheckupdate()
    {
        return checkupdate;
    }
    public String getDoctorname()
    {
        return doctorname;
    }
    public String getDoctorno()
    {
        return doctorno;
    }
    public String getDentalcaries()
    {
        return dentalcaries;
    }
    public String getRootstumps()
    {
        return rootstumps;
    }
    public String getStains()
    {
        return stains;
    }
    public String getMisaligned()
    {
        return misaligned;
    }
    public String getOral()
    {
        return oral;
    }
    public String getFlouride()
    {
        return flouride;
    }
    public String getExtraction()
    {
        return extraction;
    }
    public String getBrushtwice()
    {
        return brushtwice;
    }
    public String getOralhygiene()
    {
        return oralhygiene;
    }
    public String getOther()
    {
        return other;
    }
    public String getOrthodontics()
    {
        return orthodontics;
    }
    public String getRestoration()
    {
        return restoration;
    }
    public String getAdvise()
    {
        return advise;
    }
    public String getUid()
    {
        return uid;
    }
    public String getAccno()
    {
        return accno;
    }
    public String getAddress()
    {
        return address;
    }
    public String getMiddlename()
    {
        return middlename;
    }
    public String getLastname()
    {
        return lastname;
    }
    public String getFName()
    {
        return fname;
    }
    public String getName()
    {
        return name;
    }
    public String getCode()
    {
        return code;
    }
    public String getDate()
    {
        return date;
    }
    public String getRemarks()
    {
        return remarks;
    }
    public String getImages()
    {
        return images;
    }
    public String getDocument()
    {
        return document;
    }
    public String getContact()
    {
        return contact;
    }
    public String getPassword()
    {
        return password;
    }
    public String getStatus()
    {
        return status;
    }
    public String getDetailReason()
    {
        return detailreason;
    }
    public String getSession()
    {
        return session;
    }
    public String getId()
    {
        return id;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

