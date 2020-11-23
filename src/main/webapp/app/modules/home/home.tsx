import './home.scss';
import axios from 'axios';
import React,{ useState } from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';
import Chart from "react-google-charts";
import { IRootState } from 'app/shared/reducers';
import { AUTHORITIES } from 'app/config/constants';
export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const [ numReal, setReal] = useState(0);
  const [numFake, setFake] = useState(0);
  const { account }  = props;
  const apiUrl = 'api/articles/label/';
  function getNumReal(id) {
    axios
      .get(apiUrl+id)
      // Once we get a response, we'll map the API endpoints to our props
      .then(response =>
        setReal(response.data.num)
      )
  }
  function getNumFake(id) {
    axios
      .get(apiUrl+id)
      // Once we get a response, we'll map the API endpoints to our props
      .then(response =>
        setFake(response.data.num)
      )
  }
  // isAuthenticated();

  getNumReal(1);
  getNumFake(2);
  const real = numReal;

  const fake = numFake;
  return (

    <Row>
      <Col md="9">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

        <h2>Welcome, {props.account.login} </h2>

        <Chart
          width={'1600px'}
          height={'600px'}
          chartType="ColumnChart"
          loader={<div>Loading Chart</div>}

          data={[
            ['Year', 'Real News', 'Fake News'],
            ['Reddit', real, fake],
            ['Twitter', fake, real],

          ]}
          options={{
            vAxis: {minValue: 0},
            title: 'Statistical:',
            // is3D : true
          }}
          rootProps={{ 'data-testid': '1' }}
        />
      </Col>

    </Row>

  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});


type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
