// function LinguagensScript() {
//   return <h1>Linguagens Script!</h1>;
// }

// const styleH1 = {
//   fontFamily: 'sans-serif',
//   textDecoration: 'underline',
//   color: 'brown'
// }

// const LinguagensScript = ({name}) => {
//   return (
//     <div>
//       <h1 style={styleH1}>Bem vindo {name} à UC de Linguagens Script!</h1>
//     </div>
//   )
// }

function InfoComponent(props) {
  return (
    <div class="container">
      <div class="wrapper">
        <div class="logo">
          <img src={props.img} alt="React" />
        </div>
        <div class="text">
          <h2>{props.title}</h2>
          <p>{props.description}</p>
          <a href={props.link} target="_blank">
            Ler mais
          </a>
        </div>
      </div>
    </div>
  );
}

const containerRoot = document.getElementById("root");
ReactDOM.render(
  <React.StrictMode>
    {/* <LinguagensScript name="Maria Antunes"/>
    <LinguagensScript name="Antonio Antunes"/>
    <LinguagensScript name="Joao Antunes"/>
    <LinguagensScript name="Ze Antunes"/> */}
    <InfoComponent
      img={"images/ember.png"}
      link={"https://emberjs.com/"}
      title={"Ember"}
      description={
        "Ember.js  is  a  productive,  battle-tested  JavaScript  framework  for  building  modern  web applications. It includes everything you need to build rich UIs that work on any device."
      }
    />
    <InfoComponent
      img={"images/vue.png"}
      link={"https://vuejs.org/"}
      title={"Vue"}
      description={
        " Vue is a JavaScript  framework for building user  interfaces. It  is built on top of standard HTML, CSS and JavaScript, and provides a declarative and component-based programming model that helps you efficiently develop user interfaces, be it simple or complex."
      }
    />
  </React.StrictMode>,
  containerRoot
);
