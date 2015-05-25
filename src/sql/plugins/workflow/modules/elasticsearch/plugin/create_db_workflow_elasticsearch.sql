DROP TABLE IF EXISTS workflow_task_elasticsearch;

CREATE TABLE workflow_task_elasticsearch(
  id INT NOT NULL,
  `index` VARCHAR(255) DEFAULT 'default',
  bean_name varchar(255) not null,
  PRIMARY KEY  (id)
);
  
