
use glueballcrm;

db.counters.insert(
   {
      _id: "client_number",
      seq: 100,
      date: new Date()
   }
)

db.counters.insert(
   {
      _id: "contract_number",
      seq: 100,
      date: new Date()      
   }
)

db.counters.insert(
   {
      _id: "invoice_number",
      seq: 100000,
      date: new Date()      
   }
)

db.counters.insert(
	{
	  _id: "contact_id",
	  seq: 1
	}
)

db.counters.insert(
	{
	  _id: "phone_id",
	  seq: 1
	}
)

db.counters.insert(
	{
	  _id: "mobile_id",
	  seq: 1
	}
)

db.system.js.save(
   {
     _id : "getDailySequence" ,
     value : 
      function getDailySequence(name) {
               var seqDate = db.counters.findOne( { query: { _id: name } } ).date;
               var now = new Date();
               if ( seqDate.getDay()!=now.getDay() || 
                    seqDate.getMonth()!=now.getMonth() || 
                    seqDate.getYear()!=now.getYear() ) {
                    db.counters.findAndModify(
                              {
                                query: { _id: name },
                                update: { $set: { seq: 100, date: new Date() } },
                                new: true
                              }
                       );
               }  
               var ret = db.counters.findAndModify(
                      {
                        query: { _id: name },
                        update: { $inc: { seq: 1 } },
                        new: true
                      }
               );
               return ret.seq;
      } 
});

db.system.js.save(
           {
             _id : "getNextSequence" ,
             value : 
        function getNextSequence(name) {
           var ret = db.counters.findAndModify(
              {
                query: { _id: name },
                update: { $inc: { seq: 1 } },
                new: true
              }
            );
            return ret.seq;
       }
});
           
db.system.js.save(
        {
          _id : "getClientNumber" ,
          value :
        function getClientNumber() { 
            var d = new Date(); 
            var strDate = d.toISOString().slice(2, 10); 
            var dateArray = strDate.split("-");
            var nextId = getDailySequence("client_number");   
            return dateArray[0]+dateArray[1]+dateArray[2]+nextId;
        }
});

db.system.js.save(
        {
          _id : "getContractNumber" ,
          value :
        function getContractNumber() { 
            var d = new Date(); 
            var strDate = d.toISOString().slice(2, 10); 
            var dateArray = strDate.split("-");
            var nextId = getDailySequence("contract_number");
            return dateArray[0]+dateArray[1]+dateArray[2]+nextId;
        }
});

db.system.js.save(
        {
          _id : "getInvoiceNumber" ,
          value :
        function getInvoiceNumber() { 
            var date = new Date();  
            var nextId = getNextSequence("invoice_number");
            return date.getFullYear()+"/"+nextId;
        }
});

